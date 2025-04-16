package com.zhq.jetpackcomposelearn.ui.screen.mine.collect

import androidx.compose.runtime.mutableStateOf
import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.ArticleEditDTO
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.articles.BaseArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/14 17:25
 * Description
 */
@HiltViewModel
class CollectWebsiteViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
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

    fun handleCollectWebsite(name: String, link: String, onSuccess: () -> Unit) {
        launch({
            handleRequest(repo.handleCollectWebsite(name, link)) {
                "收藏成功".showToast()
                onSuccess.invoke()
            }
        })
    }

    fun handleEditWebsite(id: Int, name: String, link: String) {
        launch({
            handleRequest(repo.handleEditWebsite(id, name, link)) {
                "编辑成功".showToast()
                _editEvent.value= ArticleEditDTO(id = id, title = name, link = link)
            }
        })
    }
}