package com.zhq.commonlib.base.widgets

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zhq.commonlib.data.model.UiPageState
import com.zhq.commonlib.ext.ProvideItemKeys
import com.zhq.commonlib.widgets.CommonEmptyState
import com.zhq.commonlib.widgets.CommonErrorState
import com.zhq.commonlib.widgets.CommonLoadingState
import com.zhq.commonlib.widgets.LoadErrorRetryView
import com.zhq.commonlib.widgets.LoadingMoreView
import com.zhq.commonlib.widgets.NoMoreDataView
import kotlinx.coroutines.delay

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/11 13:57
 * Description
 */
private const val TAG = "BaseUiStateListPage"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : ProvideItemKeys> BaseUiStateListPage(
    modifier: Modifier = Modifier,
    uiPageState: UiPageState<List<T>>?,//页面数据与状态
    isAutoRefresh: Boolean = true,//是否进入页面自动调用加载接口onRefresh
    onRefresh: (() -> Unit)?,//刷新回调
    onLoadMore: (() -> Unit)? = null,//加载回调
    topAppBar: @Composable (() -> Unit)? = null,//AppBar
    headerContent: @Composable (() -> Unit)? = null,//内容页面Header
    isHeaderScrollWithList: Boolean = true,//Header是否和列表一起滚动
    lazyListState: LazyListState = rememberLazyListState(),
    itemSpace: Dp = 12.dp,//条目间距
    contentPadding: PaddingValues = PaddingValues(0.dp),//列表四周的间距
    loadMoreMsg: String = "拼命加载中..",
    noMoreDataMsg: String = "—— 已经到底了 ——",
    isShowFloatButton: Boolean = false,
    onFloatButtonClick: (() -> Unit)? = null,
    itemContent: @Composable LazyItemScope.(T) -> Unit//列表条目
) {
    LaunchedEffect(true) {
        if (isAutoRefresh && uiPageState?.data == null) {
            onRefresh?.invoke()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            //显示AppBar
            topAppBar?.invoke()
            if (!isHeaderScrollWithList) {
                headerContent?.invoke()
            }
            PullToRefreshBox(
                modifier = modifier.fillMaxWidth(),
                isRefreshing = uiPageState?.showRefreshing ?: false,
                onRefresh = {
                    onRefresh?.invoke()
                }) {

                if (uiPageState?.showLoadingPage == true) {//显示页面加载
                    CommonLoadingState()
                } else if (uiPageState?.showEmptyPage == true
                    && !uiPageState.showRefreshing
                    && (uiPageState.data == null || uiPageState.data?.isEmpty() == true)
                ) {//显示空数据页面
                    CommonEmptyState(msg = uiPageState.msg)
                } else if (uiPageState?.showErrorPage == true) {//显示异常错误页面
                    CommonErrorState(msg = uiPageState.errorMsg) {
                        onRefresh?.invoke()
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = lazyListState,
                        contentPadding = contentPadding,
                        verticalArrangement = Arrangement.spacedBy(itemSpace)
                    ) {
                        if (isHeaderScrollWithList) {
                            item { headerContent?.invoke() }
                        }
                        uiPageState?.data?.let {
                            items(it, key = { item -> item.provideKey() }) { t ->
                                itemContent(t)
                            }
                            //存在上拉加载
                            if (onLoadMore != null) {
                                item {
                                    //正在上拉加载
                                    if (uiPageState.showLoadingMore) {
                                        LoadingMoreView(msg = loadMoreMsg)
                                        LaunchedEffect(Unit) {
                                            delay(500)
                                            onLoadMore()
                                        }
                                    }
                                }

                                item {
                                    if (uiPageState.showLoadMoreError) {
                                        LoadErrorRetryView(message = uiPageState.errorMsg) {
                                            onLoadMore.invoke()
                                        }
                                    }
                                }


                            }
                            item {
                                //已经到底了
                                if (uiPageState.showNoMoreData) {
                                    NoMoreDataView(msg = noMoreDataMsg)
                                }
                            }
                        }

                    }

                }

            }

        }
        if (isShowFloatButton) {
            FloatingActionButton(
                onClick = {
                    onFloatButtonClick?.invoke() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 12.dp, bottom = 12.dp),
                containerColor = Color.Yellow,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "添加")
            }
        }
    }

}