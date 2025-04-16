package com.zhq.jetpackcomposelearn.ui.screen.mine.collect

import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.data.ArticleEditDTO
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
class CollectArticleViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseArticleViewModel(repo) {

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
                    }) {
                    articleList.apply { addAll(it.data.datas) }
                    if (it.data.over) {
                        showContent(data = articleList, isLoadOver = true)
                        return@handleRequest
                    }
                    showContent(data = articleList, false)
                }
            }

        })
    }

    fun postCollectArticleForExternal(
        title: String,
        author: String,
        link: String,
        onSuccess: () -> Unit
    ) {
        launch({
            handleRequest(repo.postCollectArticleForExternal(title, author, link)) {
                "收藏成功".showToast()
                onSuccess.invoke()
            }
        })
    }

    fun handleEditCollectedArticle(articleId: Int, title: String, author: String, link: String) {
        launch({
            handleRequest(repo.handleEditCollectedArticle(articleId, title, author, link)) {
                "编辑成功".showToast()
                _editEvent.value =
                    ArticleEditDTO(id = articleId, title = title, author = author, link = link)
            }
        })
    }
}