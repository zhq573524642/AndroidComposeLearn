package com.zhq.jetpackcomposelearn.ui.screen.systems

import com.zhq.jetpackcomposelearn.repo.SystemsRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 13:57
 * Description
 */
@HiltViewModel
class SystemChildViewModel @Inject constructor(private val repo: SystemsRepositoryImpl) :
    ArticleViewModel(repo) {


    fun getSystemChildList(cid: Int, isRefresh: Boolean) {
        emitUiState(
            showLoading = isRefresh,
            data = articleList
        )
        launch({
            if (isRefresh) {
                currentPage = 0
                articleList.clear()
            }

            if (currentPage == 0) {
                handleRequest(repo.getSystemChildList(currentPage++, cid),
                    errorBlock = {
                        emitUiState(
                            showLoading = false,
                            data = articleList,
                            showLoadingMore = false,
                            error = it.errorMsg
                        )
                        true
                    }) {
                   emitUiState(
                       showLoading = false,
                       data = articleList.apply { addAll(it.data.datas) },
                       showLoadingMore = true,
                   )
                }
            }else{
                handleRequest(repo.getSystemChildList(currentPage++,cid)){
                    articleList.addAll(it.data.datas)
                    if (it.data.over) {
                        emitUiState(
                            data =
                            articleList, showLoadingMore = false, noMoreData = true
                        )
                        return@handleRequest
                    }
                    currentPage++

                    emitUiState(data = articleList, showLoadingMore = true)
                }
            }
        })
    }
}