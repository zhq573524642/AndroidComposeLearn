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
class CourseCatalogViewModel @Inject constructor(private val repo: CourseRepositoryImpl) :
    ArticleViewModel(repo) {

    fun getCourseCatalogList(cid: Int, isRefresh: Boolean) {
        emitUiState(
            showLoading = isRefresh,
            data = articleList,
        )

        launch({
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }
            if (currentPage == 0) {
                handleRequest(repo.getCourseCatalogList(currentPage, cid)) {
                    currentPage++
                    emitUiState(
                        showLoadingMore = true,
                        data = articleList.apply { addAll(it.data.datas) }
                    )
                }
            } else {
                handleRequest(repo.getCourseCatalogList(currentPage, cid)) {
                    articleList.addAll(it.data.datas)
                    if (it.data.over) {
                        emitUiState(
                            data =
                            articleList, showLoadingMore = false, noMoreData = true
                        )
                        return@handleRequest
                    }
                    currentPage = it.data.curPage

                    emitUiState(data = articleList, showLoadingMore = true)
                }
            }

        })
    }
}