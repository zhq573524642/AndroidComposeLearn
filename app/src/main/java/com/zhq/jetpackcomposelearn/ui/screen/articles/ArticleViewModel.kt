package com.zhq.jetpackcomposelearn.ui.screen.articles

import com.zhq.jetpackcomposelearn.data.CoinInfoDTO
import com.zhq.jetpackcomposelearn.repo.ArticleRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 13:40
 * Description
 */
@HiltViewModel
class ArticleViewModel @Inject constructor(private val repo: ArticleRepositoryImpl) :
    BaseArticleViewModel(repo) {

    private val _coinInfo = MutableStateFlow<CoinInfoDTO>(CoinInfoDTO())
    val coinInfo: StateFlow<CoinInfoDTO> get() = _coinInfo

    private val _articleCount=MutableStateFlow<Int>(0)

    val articleCount:StateFlow<Int> get() = _articleCount

    //获取公众号历史文章列表
    fun getArticleHistoryForOfficialAccount(isRefresh: Boolean, id: Int) {
        showLoading(isRefresh, data = articleList)
        if (isRefresh) {
            currentPage = 1
            articleList.clear()
        }
        launch({
            if (currentPage == 1) {
                handleRequest(repo.getArticlesForOfficialAccount(id, currentPage),
                    errorBlock = {
                        showError(msg = it.errorMsg)
                        true
                    }) {
                    articleList.apply { addAll(it.data.datas) }
                    if (articleList.isNotEmpty()) {
                        currentPage = it.data.curPage + 1
                        showContent(data = articleList, isLoadOver = it.data.over)
                    } else {
                        showEmpty()
                    }
                }
            } else {
                handleRequest(repo.getArticlesForOfficialAccount(id, currentPage),
                    errorBlock = {
                        showLoadMoreError(data = articleList)
                        true
                    }) {
                    articleList.apply { addAll(it.data.datas) }
                    if (it.data.over) {
                        showContent(data = articleList, true)
                        return@handleRequest
                    }
                    currentPage = it.data.curPage + 1
                    showContent(data = articleList, false)
                }
            }
        })
    }

    //获取广场数据列表
    fun getSquareArticleList(isRefresh: Boolean) {
        showLoading(isClearContent = isRefresh, data = articleList)
        if (isRefresh) {
            currentPage = 0
            articleList.clear()
        }
        launch({
            if (currentPage == 0) {
                handleRequest(repo.getSquareArticleList(currentPage),
                    errorBlock = {
                        showError(msg = it.errorMsg)
                        true
                    }) {
                    articleList.addAll(it.data.datas)
                    if (articleList.isNotEmpty()) {
                        currentPage = it.data.curPage
                        showContent(data = articleList, isLoadOver = it.data.over)
                    } else {
                        showEmpty()
                    }
                }
            } else {
                handleRequest(repo.getSquareArticleList(currentPage),
                    errorBlock = {
                        showLoadMoreError(data = articleList, msg = it.errorMsg)
                        true
                    }) {
                    articleList.addAll(it.data.datas)
                    if (it.data.over) {
                        showContent(data = articleList, true)
                        return@handleRequest
                    }
                    currentPage = it.data.curPage
                    showContent(data = articleList, false)
                }
            }
        })
    }

    //获取分享人对应文章列表
    fun getArticleListForShareUser(isRefresh: Boolean, id: Int) {
        if (isRefresh) {
            currentPage = 1
            articleList.clear()
        }
        launch({
            if (currentPage == 1) {
                handleRequest(repo.getArticleListForShareUser(currentPage, id),
                    errorBlock = {
                        showError(msg = it.errorMsg)
                        true
                    }) {
                    _coinInfo.value = it.data.coinInfo
                    _articleCount.value=it.data.shareArticles.total
                    articleList.addAll(it.data.shareArticles.datas)
                    if (articleList.isNotEmpty()) {
                        currentPage = it.data.shareArticles.curPage + 1
                        showContent(data = articleList, isLoadOver = it.data.shareArticles.over)
                    } else {
                        showEmpty("暂无任何分享")
                    }
                }
            } else {
                handleRequest(repo.getArticleListForShareUser(currentPage, id),
                    errorBlock = {
                        showLoadMoreError(data = articleList, msg = it.errorMsg)
                        true
                    }) {
                    articleList.addAll(it.data.shareArticles.datas)
                    if (it.data.shareArticles.over) {
                        showContent(data = articleList, true)
                        return@handleRequest
                    }
                    currentPage = it.data.shareArticles.curPage + 1
                    showContent(data = articleList, false)
                }
            }
        })
    }
}