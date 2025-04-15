package com.zhq.commonlib.base.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zhq.commonlib.data.model.UiPageState
import com.zhq.commonlib.ext.ProvideItemKeys
import com.zhq.commonlib.ext.isLightColor

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 16:14
 * Description
 */
private const val TAG = "BaseRefreshListContaine"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : ProvideItemKeys> BaseRefreshListContainer(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,//是否全屏显示（是否布局延伸到状态栏）
    statusBarColor: Color = Color(0xfff5f5f5),//状态栏颜色
    backgroundColor: Color = Color(0xfff5f5f5),//内容背景色
    uiPageState: UiPageState<List<T>>?,//页面数据与状态
    isAutoRefresh: Boolean = true,//是否进入页面自动调用加载接口onRefresh
    onRefresh: (() -> Unit)?,//刷新回调
    onLoadMore: (() -> Unit)? = null,//加载回调
    topAppBar: @Composable (() -> Unit)? = null,//AppBar
    isHeaderScrollWithList: Boolean = true,//Header是否和列表一起滚动
    headerContent: @Composable (() -> Unit)? = null,//内容页面Header
    lazyListState: LazyListState = rememberLazyListState(),
    itemSpace: Dp = 12.dp,//条目间距
    contentPadding: PaddingValues = PaddingValues(0.dp),//列表四周的间距
    loadMoreMsg: String = "拼命加载中..",
    noMoreDataMsg: String = "—— 已经到底了 ——",
    itemContent: @Composable (T) -> Unit//列表条目
) {

    // 动态设置状态栏透明度
    val systemUiController = rememberSystemUiController()
    val statusColor = if (isFullScreen) Color.Transparent else statusBarColor
    systemUiController.setStatusBarColor(
        color = statusColor,
        darkIcons = if (isFullScreen) false else statusBarColor.isLightColor()
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            // 根据需求动态添加内边距
            .then(if (!isFullScreen) Modifier.statusBarsPadding() else Modifier)
            //解决状态来手势冲突
            .then(if (isFullScreen) Modifier.systemGestureExclusion() else Modifier)
    ) {
        BaseUiStateListPage(
            modifier = modifier,
            uiPageState = uiPageState,
            lazyListState = lazyListState,
            itemSpace = itemSpace,
            contentPadding = contentPadding,
            onRefresh = { onRefresh?.invoke() },
            onLoadMore = { onLoadMore?.invoke() },
            isAutoRefresh = isAutoRefresh,
            loadMoreMsg = loadMoreMsg,
            noMoreDataMsg = noMoreDataMsg,
            topAppBar = {
                topAppBar?.invoke()
            },
            isHeaderScrollWithList = isHeaderScrollWithList,
            headerContent = {
                headerContent?.invoke()
            }) {
            itemContent.invoke(it)
        }
    }
}