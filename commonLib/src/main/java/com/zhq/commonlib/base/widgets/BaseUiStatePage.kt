package com.zhq.commonlib.base.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.zhq.commonlib.data.model.UiPageState
import com.zhq.commonlib.ext.ProvideItemKeys
import com.zhq.commonlib.widgets.CommonEmptyState
import com.zhq.commonlib.widgets.CommonErrorState
import com.zhq.commonlib.widgets.CommonLoadingState

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 10:55
 * Description
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : ProvideItemKeys> BaseUiStatePage(
    modifier: Modifier = Modifier,
    uiPageState: UiPageState<List<T>>?,//页面数据与状态
    isAutoRefresh: Boolean = true,//是否进入页面自动调用加载接口onRefresh
    onRefresh: (() -> Unit)?,//刷新回调
    topAppBar: @Composable (() -> Unit)? = null,//AppBar
    content: @Composable (List<T>) -> Unit
) {
    LaunchedEffect(true) {
        if (isAutoRefresh && uiPageState?.data == null) {
            onRefresh?.invoke()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //显示AppBar
        topAppBar?.invoke()

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
                && (uiPageState.data == null)
            ) {//显示空数据页面
                CommonEmptyState(msg = uiPageState.msg)
            } else if (uiPageState?.showErrorPage == true) {//显示异常错误页面
                CommonErrorState(msg = uiPageState.errorMsg) {
                    onRefresh?.invoke()
                }
            } else {
                uiPageState?.data?.let {
                    content.invoke(it)
                }
            }

        }
    }
}