package com.zhq.jetpackcomposelearn.ui.screen.course

import android.util.Log
import com.zhq.jetpackcomposelearn.repo.CourseRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 15:09
 * Description
 */
@HiltViewModel
class CourseViewModel @Inject constructor(private val repo: CourseRepositoryImpl) :
    ArticleViewModel(repo) {

    fun getCourseList(isRefresh: Boolean) {
        emitUiState(
            showLoading = isRefresh,
            data = articleList,
            noMoreData = true
        )

        launch({
            handleRequest(repo.getCourseList()) {
                articleList.clear()
                emitUiState(
                    showLoadingMore = false,
                    noMoreData = true,
                    data = articleList.apply { addAll(it.data) }
                )
            }
        })
    }
}