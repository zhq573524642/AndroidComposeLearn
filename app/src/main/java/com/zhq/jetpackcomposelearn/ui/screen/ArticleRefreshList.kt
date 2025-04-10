package com.zhq.jetpackcomposelearn.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.data.ArticleDTO


/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 14:24
 * Description
 */
@Composable
fun ArticleRefreshList(
    modifier: Modifier = Modifier,
    itemSpace: Dp = 12.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: ArticleViewModel,
    lazyListState: LazyListState = rememberLazyListState(),
    onRefresh: (() -> Unit)?,
    onLoadMore: (() -> Unit)? = null,
    headerContent: @Composable (() -> Unit)? = null,
    itemContent: @Composable (ArticleDTO) -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    val collectData by App.appViewModel.collectEvent.observeAsState()
    val user by App.appViewModel.user.collectAsState()
    val unCollectEvent by viewModel.unCollectEvent.collectAsState()

    // 收藏事件监听
    if (collectData != null) {
        uiPageState.data?.forEach {
            if (it.id == collectData!!.id) {
                it.collect = collectData!!.collect
            }
        }
    }

    // 我收藏的文章列表中取消收藏
    if (unCollectEvent != null) {
        uiPageState.data = uiPageState.data?.filter {
            it.id != unCollectEvent
        }
    }

    // 用户退出时，收藏应全为false，登录时获取collectIds
    if (user == null) {
        uiPageState.data?.forEach {
            it.collect = false
        }
    } else {
        uiPageState.data?.forEach {
            user?.userInfo?.collectIds?.forEach { id ->
                if (id == it.id) {
                    it.collect = true
                }
            }
        }
    }

    BaseRefreshListContainer(
        modifier = modifier,
        itemSpace = itemSpace,
        contentPadding = contentPadding,
        uiPageState = uiPageState,
        lazyListState = lazyListState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        headerContent = headerContent,
        itemContent = itemContent
    )
}
