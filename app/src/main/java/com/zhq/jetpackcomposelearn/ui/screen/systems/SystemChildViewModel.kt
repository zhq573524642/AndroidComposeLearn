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
        showLoading(isRefresh, data = articleList)
        launch({
            if (isRefresh) {
                currentPage = 0
                articleList.clear()

            }

            if (currentPage == 0) {
                handleRequest(repo.getSystemChildList(currentPage++, cid),
                    errorBlock = {
                        showError(msg = it.errorMsg)
                        true
                    }) {
                    articleList.apply { addAll(it.data.datas) }
                    if (articleList.isEmpty()){
                        showEmpty()
                    }else{
                        showContent(data = articleList, isLoadOver = it.data.over)
                    }
                }
            } else {
                handleRequest(repo.getSystemChildList(currentPage++, cid),
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
}