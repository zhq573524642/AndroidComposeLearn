package com.zhq.jetpackcomposelearn.common

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhq.commonlib.data.model.UiState
import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.R
import kotlinx.coroutines.delay
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.ui.unit.Dp
import kotlin.math.log

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 14:34
 * Description
 */
private const val TAG = "CommonRefreshList"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : ProvideItemKeys> CommonRefreshList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemSpace: Dp = 12.dp,
    uiState: UiState<List<T>>?,
    lazyListState: LazyListState = rememberLazyListState(),
    onRefresh: (() -> Unit)?,
    onLoadMore: (() -> Unit)? = null,
    headerContent: @Composable (() -> Unit)? = null,
    itemContent: @Composable (T) -> Unit
) {

    LaunchedEffect(true) {
        if (uiState?.data == null) {
            onRefresh?.invoke()
        }
    }
    PullToRefreshBox(
        modifier = modifier.fillMaxWidth(),
        isRefreshing = uiState?.showLoading ?: false, onRefresh = {
            onRefresh?.invoke()
        }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = lazyListState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(itemSpace)
        ) {
            item {
                headerContent?.invoke()
            }
            uiState?.data?.let {
                items(it, key = { item -> item.provideKey() }) { t ->
                    itemContent(t)
                }
                if (onLoadMore != null) {
                    item {
                        if (uiState.showLoadingMore) {
                            LoadingMoreView()
                            LaunchedEffect(Unit) {
                                delay(500)
                                onLoadMore()
                            }
                        }
                    }
                    item {
                        if (uiState.noMoreData) {
                           NoMoreDataView()
                        }
                    }
                }
            }


        }

        if (uiState?.showLoading != true && (uiState?.data == null || uiState.data?.isEmpty() == true)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_list_empty),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "列表为空",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
