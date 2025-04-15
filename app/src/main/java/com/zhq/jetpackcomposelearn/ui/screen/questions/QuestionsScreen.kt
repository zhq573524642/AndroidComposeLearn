package com.zhq.jetpackcomposelearn.ui.screen.questions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zhq.commonlib.widgets.CommonEmptyState
import com.zhq.commonlib.widgets.CommonErrorState
import com.zhq.commonlib.widgets.CommonLoadingState
import com.zhq.commonlib.widgets.LoadErrorRetryView
import com.zhq.commonlib.widgets.LoadingMoreView
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.common.BigTitleHeader
import com.zhq.jetpackcomposelearn.common.DynamicStatusBarScreen
import com.zhq.jetpackcomposelearn.data.ArticleDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 14:25
 * Description
 */
@Composable
fun QuestionsScreen(
    viewModel: QuestionsViewModelBase = hiltViewModel(),
    onSquareClick: () -> Unit,
    onQuestionItemClick: (ArticleDTO) -> Unit

) {

    val datas = remember { viewModel.getQuestionsList() }.collectAsLazyPagingItems()

    val state: LazyListState = rememberLazyListState()

    val collectData by App.appViewModel.collectEvent.observeAsState()
    val user by App.appViewModel.user.collectAsState()

    // 收藏事件监听
    if (collectData != null) {
        datas.itemSnapshotList.items.forEach {
            if (it.id == collectData!!.id) {
                it.collect = collectData!!.collect
            }
        }
    }
    // 用户退出时，收藏应全为false，登录时获取collectIds
    if (user == null) {
        datas.itemSnapshotList.items.forEach {
            it.collect = false
        }
    } else {
        datas.itemSnapshotList.items.forEach {
            user?.userInfo?.collectIds?.forEach { id ->
                if (id == it.id) {
                    it.collect = true
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            datas.refresh()
        }
    }

    DynamicStatusBarScreen(
        statusBarColor = Color(0xfff6f6f6),
        backgroundColor = Color(0xfff5f5f5)
    ) {
        Column {
            BigTitleHeader(title = "问答") {
                Text(text = "广场",
                    color = colorResource(id = R.color.purple_500),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.clickable {
                        onSquareClick.invoke()
                    })
            }

            LazyColumn(
                state = state,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp)

            ) {

                items(datas.itemCount) { index ->
                    val data = datas[index]
                    if (data != null) {
                        QuestionItem(data = data, viewModel = viewModel) { item: ArticleDTO ->
                            onQuestionItemClick.invoke(item)
                        }
                    } else {
                        CommonEmptyState()
                    }
                }

                when (datas.loadState.refresh) {
                    is LoadState.NotLoading -> {
//                Log.d(TAG, "===NotLoading: ")
                    }

                    is LoadState.Loading -> {
//                Log.d(TAG, "===Loading: ")
                        item {
                            CommonLoadingState()
                        }
                    }

                    is LoadState.Error -> {
                        if (datas.itemCount <= 0) {
                            item {
                                //显示全屏错误
                                CommonErrorState {
                                    datas.retry()
                                }
                            }
                        }
                    }
                }

                when (datas.loadState.append) {
                    is LoadState.NotLoading -> {}
                    is LoadState.Loading -> {
//                Log.d(TAG, "===append-Loading: ")
                        item {
                            LoadingMoreView()
                        }
                    }

                    is LoadState.Error -> {
                        if (datas.itemCount > 0) {
//                    Log.d(TAG, "===append-error: ")
                            /**
                             * 底部显示加载错误*/
                            item {
                                LoadErrorRetryView(message = "加载错误") {
                                    datas.retry()
                                }
                            }
                        }
                    }
                }

            }


        }
    }

}