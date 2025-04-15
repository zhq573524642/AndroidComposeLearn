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
class CourseViewModelBase @Inject constructor(private val repo: CourseRepositoryImpl) :
    BaseArticleViewModel(repo) {

    fun getCourseList(isRefresh: Boolean) {
        showLoading(articleList.isEmpty(), data = articleList)

        launch({
            handleRequest(repo.getCourseList(),
                errorBlock = {
                    showError(msg = it.errorMsg)
                    true
                }) {
                articleList.clear()
                articleList.apply { addAll(it.data) }
                showContent(data = articleList, isLoadOver = true)
            }
        })
    }
}