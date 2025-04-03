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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.common.BigTitleHeader

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 14:25
 * Description
 */
@Composable
fun QuestionsScreen(
    viewModel: QuestionsViewModel = hiltViewModel(),
    onSquareClick: () -> Unit

) {
    val datas =
        viewModel.getQuestionsList().collectAsLazyPagingItems()

    val state: LazyListState = rememberLazyListState()
    Column {
        BigTitleHeader(title = "问答") {
            Text(text = "广场",
                color = colorResource(id = R.color.purple_500),
                fontSize = 13.sp,
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
                   QuestionItem(data = data)
                }/*else{
                EmptyScreen()
            }*/
            }

//        when (datas.loadState.refresh) {
//            is LoadState.NotLoading -> {
////                Log.d(TAG, "===NotLoading: ")
//            }
//
//            is LoadState.Loading -> {
////                Log.d(TAG, "===Loading: ")
//                item {
//                    LoadingScreen()
//                }
//            }
//
//            is LoadState.Error -> {
//                if (datas.itemCount <= 0) {
//                    item {
//                        //显示全屏错误
//                        ErrorScreen()
//                    }
//                }
//            }
//        }
//
//
//
//        when (datas.loadState.append) {
//            is LoadState.NotLoading -> {}
//            is LoadState.Loading -> {
////                Log.d(TAG, "===append-Loading: ")
//                item {
//                    BottomLoading()
//                }
//            }
//
//            is LoadState.Error -> {
//                if (datas.itemCount > 0) {
////                    Log.d(TAG, "===append-error: ")
//                    /**
//                     * 底部显示加载错误*/
//                    item {
//                        BottomError()
//                    }
//                }
//            }
//        }

        }


    }
}