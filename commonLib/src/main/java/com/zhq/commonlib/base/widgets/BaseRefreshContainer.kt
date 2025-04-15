package com.zhq.commonlib.base.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zhq.commonlib.data.model.UiPageState
import com.zhq.commonlib.ext.ProvideItemKeys
import com.zhq.commonlib.ext.isLightColor
import com.zhq.commonlib.widgets.CommonEmptyState
import com.zhq.commonlib.widgets.CommonErrorState
import com.zhq.commonlib.widgets.CommonLoadingState
import com.zhq.commonlib.widgets.LoadingMoreView
import com.zhq.commonlib.widgets.NoMoreDataView
import kotlinx.coroutines.delay

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 16:14
 * Description
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : ProvideItemKeys> BaseRefreshContainer(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,//是否全屏显示（是否布局延伸到状态栏）
    statusBarColor: Color = Color(0xfff5f5f5),//状态栏颜色
    backgroundColor: Color = Color(0xfff5f5f5),//内容背景色
    uiPageState: UiPageState<List<T>>?,//页面数据与状态
    isAutoRefresh: Boolean = true,//是否进入页面自动调用加载接口onRefresh
    onRefresh: (() -> Unit)?,//刷新回调
    topAppBar: @Composable (() -> Unit)? = null,//AppBar
    content: @Composable (List<T>) -> Unit
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

        BaseUiStatePage(
            modifier = modifier,
            uiPageState = uiPageState,
            isAutoRefresh = isAutoRefresh,
            onRefresh = { onRefresh?.invoke() },
            topAppBar = { topAppBar?.invoke() }) {
            content.invoke(it)
        }
    }
}