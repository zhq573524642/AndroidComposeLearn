package com.zhq.jetpackcomposelearn.ui.screen.mine.collect

import com.zhq.commonlib.utils.ToastUtils.showToast
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
class CollectWebsiteViewModelBase @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseArticleViewModel(repo) {

    fun getMyCollectWebsite(isRefresh: Boolean = true) {
        showLoading(isRefresh, data = articleList)
        launch({
            if (isRefresh) {
                articleList.clear()
            }
            handleRequest(repo.getMyCollectWebsite(),
                errorBlock = {
                    showError(it.errorMsg)
                    true
                }) {
                articleList.apply { addAll(it.data) }
                if (articleList.isEmpty()) {
                    showEmpty()
                } else {
                    showContent(data = articleList, true)
                }
            }
        })
    }

    fun deleteCollectWebsite(id: Int) {
        launch({
            handleRequest(repo.deleteCollectWebsite(id)) {
                _unCollectEvent.value = id
                "已取消收藏".showToast()
            }
        })
    }
}