package com.zhq.jetpackcomposelearn.ui.screen.course

import com.zhq.jetpackcomposelearn.repo.CourseRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.articles.BaseArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 15:09
 * Description
 */
@HiltViewModel
class CourseCatalogViewModelBase @Inject constructor(private val repo: CourseRepositoryImpl) :
    BaseArticleViewModel(repo) {

    fun getCourseCatalogList(cid: Int, isRefresh: Boolean) {

        showLoading(isClearContent = articleList.isEmpty(), data = articleList)

        launch({
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }
            if (currentPage == 0) {
                handleRequest(repo.getCourseCatalogList(currentPage, cid),
                    errorBlock = {
                        showError(it.errorMsg)
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
                handleRequest(repo.getCourseCatalogList(currentPage, cid),
                    errorBlock = {
                        showLoadMoreError(data = articleList, msg = it.errorMsg)
                        true
                    }) {
                    articleList.addAll(it.data.datas)
                    if (it.data.over) {
                        showContent(data = articleList, isLoadOver = true)
                        return@handleRequest
                    }
                    currentPage = it.data.curPage

                    showContent(data = articleList, isLoadOver = false)
                }
            }

        })
    }
}