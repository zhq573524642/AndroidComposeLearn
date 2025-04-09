package com.zhq.jetpackcomposelearn.ext

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/7 11:27
 * Description
 */

// 自动加载扩展函数
@Composable
fun AutoLoadMoreHandler(
    listState: LazyListState,
    buffer: Int = 3,
    onLoadMore: () -> Unit
) {
    val shouldLoadMore by remember(listState) {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) return@derivedStateOf false

            val lastVisibleItem = visibleItems.last()
            lastVisibleItem.index >= layoutInfo.totalItemsCount - buffer - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }
}