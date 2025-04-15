package com.zhq.jetpackcomposelearn.ui.screen.mine.collect

import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.articles.BaseArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/14 17:25
 * Description
 */
@HiltViewModel
class CollectBaseArticleViewModel @Inject constructor(private val repo:MineRepositoryImpl) :
    BaseArticleViewModel(repo){

    fun getMyCollectArticle(isRefresh: Boolean = true) {
        showLoading(isRefresh, data = articleList)
        launch({
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }
            if (currentPage == 0) {
                handleRequest(repo.getMyCollectArticle(currentPage),
                    errorBlock = {
                        showError(it.errorMsg)
                        true
                    }) {
                    articleList.apply { addAll(it.data.datas) }
                    if (articleList.isEmpty()) {
                        showEmpty()
                    } else {
                        showContent(data = articleList, it.data.over)
                        currentPage = it.data.curPage
                    }
                }
            } else {
                handleRequest(repo.getMyCollectArticle(currentPage),
                    errorBlock = {
                        showLoadMoreError(data = articleList)
                        true
                    }){
                    articleList.apply { addAll(it.data.datas) }
                    if (it.data.over){
                        showContent(data = articleList, isLoadOver = true)
                        return@handleRequest
                    }
                    showContent(data = articleList,false)
                }
            }

        })
    }
}