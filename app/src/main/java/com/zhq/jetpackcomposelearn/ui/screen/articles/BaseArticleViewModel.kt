package com.zhq.jetpackcomposelearn.ui.screen.articles


import com.zhq.commonlib.base.BaseViewModel
import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.ArticleEditDTO
import com.zhq.jetpackcomposelearn.data.CollectDataDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 14:24
 * Description
 */
@HiltViewModel
open class BaseArticleViewModel @Inject constructor(private val repo: BaseRepository) :
    BaseViewModel<List<ArticleDTO>>() {

    //取消收藏的文章或者网站的id
    protected val _unCollectEvent = MutableStateFlow<Int?>(null)
    val unCollectEvent: StateFlow<Int?> = _unCollectEvent

    //编辑之后的文章或者网站
    protected val _editEvent = MutableStateFlow<ArticleEditDTO?>(null)
    val editEvent: StateFlow<ArticleEditDTO?> get() = _editEvent

    companion object {
        /** 每页显示的条目大小 */
        const val PAGE_SIZE = 10
    }

    protected val articleList = ArrayList<ArticleDTO>()
    protected var currentPage = 0
    protected val cacheArticleList = ArrayList<ArticleDTO>()

    fun handleCollectArticle(
        isCollect: Boolean,
        id: Int,
        onError: (String) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        launch({
            handleRequest(repo.handleCollectArticle(isCollect, id),
                errorBlock = {
                    onError.invoke(it.errorMsg)
                    it.errorMsg.showToast()
                    false
                }
            ) {
                App.appViewModel.emitCollectEvent(CollectDataDTO(id = id, collect = isCollect))
                if (isCollect) {
                    "收藏成功".showToast()
                } else {
                    "已取消收藏".showToast()
                }
                onSuccess.invoke()
            }
        })
    }

    fun handleCollectArticleForMine(
        isCollect: Boolean,
        id: Int,
        originId: Int,
        onError: (String) -> Unit = {},
        onSuccess: () -> Unit = {}
    ) {
        launch({
            handleRequest(repo.handleCollectArticle(isCollect, id, true, originId),
                errorBlock = {
                    onError.invoke(it.errorMsg)
                    it.errorMsg.showToast()
                    false
                }
            ) {
                App.appViewModel.emitCollectEvent(CollectDataDTO(id = id, collect = isCollect))
                _unCollectEvent.value = id
                if (isCollect) {
                    "收藏成功".showToast()
                } else {
                    "已取消收藏".showToast()
                }
                onSuccess.invoke()
            }
        })
    }




}