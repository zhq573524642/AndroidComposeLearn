package com.zhq.jetpackcomposelearn.ui.screen.home

import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.BannerDTO
import com.zhq.jetpackcomposelearn.repo.HomeRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.articles.BaseArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 15:55
 * Description
 */
@HiltViewModel
class HomeViewModelBase @Inject constructor(private val repo: HomeRepositoryImpl) :
    BaseArticleViewModel(repo) {

    private val _bannerList = MutableStateFlow<List<BannerDTO>>(emptyList())
    val bannerList: StateFlow<List<BannerDTO>> get() = _bannerList

    private val _unreadCount = MutableStateFlow<Int>(0)
    val unreadCount: StateFlow<Int> get() = _unreadCount

    fun getBannerData() {
        launch({
            handleRequest(repo.getBannerData()) {
                _bannerList.value = it.data
            }
        })
    }

    fun getHomeArticleList(isRefresh: Boolean = true) {
        showLoading(articleList.isEmpty(), articleList)
        launch(tryBlock = {
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }

            if (currentPage == 0) {
                // 第一页会同时请求置顶文章列表接口和分页文章列表的接口，使用async进行并行请求速度更快（默认是串行的）
                // 需要加SupervisorJob()来自行处理协程
                val job1 = async(Dispatchers.IO + SupervisorJob()) {
                    repo.getHomeArticleList(currentPage)
                }
                val job2 = async(Dispatchers.IO + SupervisorJob()) {
                    repo.getHomeTopArticleList()
                }

                try {
                    val response1 = job1.await()//文章列表
                    val response2 = job2.await()//置顶文章列表

                    handleRequest(response1) {
                        handleRequest(response2,
                            errorBlock = {
                                showError(it.errorMsg)
                                true
                            }) {
                            (response1.data.datas as ArrayList<ArticleDTO>).apply {
                                addAll(0, response2.data)
                            }
                            articleList.apply { addAll(response1.data.datas) }
                            if (articleList.isEmpty()) {
                                showEmpty(msg = "这里什么也没有")
                            } else {
                                currentPage++
                                showContent(
                                    data = articleList,
                                    isLoadOver = response1.data.over
                                )
                            }


                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    showError(msg = e.message.toString())
                }
            } else {
                handleRequest(repo.getHomeArticleList(currentPage),
                    errorBlock = {
                        showLoadMoreError(data = articleList, msg = it.errorMsg)
                        true
                    }) {
                    articleList.addAll(it.data.datas)
                    if (it.data.over) {
                        showContent(data = articleList, isLoadOver = true)
                        return@handleRequest
                    }
                    currentPage++
                    showContent(data = articleList, isLoadOver = false)

                }
            }
        })
    }

    fun getUnreadMsgCount() {
        launch({
            handleRequest(repo.getUnreadMsgCount()) {
                _unreadCount.value = it.data
            }
        })
    }
}