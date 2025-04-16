package com.zhq.jetpackcomposelearn.ui.screen.systems

import com.zhq.jetpackcomposelearn.repo.SystemsRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.articles.BaseArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 13:57
 * Description
 */
@HiltViewModel
class SystemChildViewModel @Inject constructor(private val repo: SystemsRepositoryImpl) :
    BaseArticleViewModel(repo) {


    fun getSystemChildList(cid: Int, isRefresh: Boolean) {
        showLoading(isClearContent = articleList.isEmpty(), data = articleList)
        launch({
            if (isRefresh) {
                articleList.clear()
                currentPage = 0

            }

            if (currentPage == 0) {
                handleRequest(repo.getSystemChildList(currentPage, cid),
                    errorBlock = {
                        showError(msg = it.errorMsg)
                        true
                    }) {
                    currentPage++
                    articleList.apply { addAll(it.data.datas) }
                    if (articleList.isEmpty()){
                        showEmpty()
                    }else{
                        showContent(data = articleList, isLoadOver = it.data.over)
                    }
                }
            } else {
                handleRequest(repo.getSystemChildList(currentPage, cid),
                    errorBlock = {
                        showLoadMoreError(data = articleList, msg = it.errorMsg)
                        true
                    }) {
                    articleList.addAll(it.data.datas)
                    if (it.data.over) {
                        showContent(data = articleList, isLoadOver = true)
                        return@handleRequest
                    }
                    currentPage=it.data.curPage
                    showContent(data = articleList, isLoadOver = false)
                }
            }
        })
    }
}