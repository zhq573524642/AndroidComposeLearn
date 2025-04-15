package com.zhq.jetpackcomposelearn.ui.screen.message

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zhq.commonlib.base.widgets.BaseUiStateListPage
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.common.HorizontalSpace
import com.zhq.jetpackcomposelearn.data.MessageDTO
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/14 10:58
 * Description
 */
private const val TAG = "MessageScreen"

@Serializable
data object MessageRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    navHostController: NavHostController,
) {

    val tabList = listOf("已读", "未读")

    val pageState = rememberPagerState(initialPage = 0) {
        tabList.size
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        CenterTitleHeader(title = "消息", onBack = { navHostController.popBackStack() })
        PrimaryTabRow(
            selectedTabIndex = pageState.currentPage, divider = {},
            modifier = Modifier
                .fillMaxWidth(),
            containerColor = Color(0xfff5f5f5),
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        pageState.currentPage,
                        matchContentSize = true
                    ),
                    color = Color.Black,
                    width = Dp.Unspecified,
                )
            }
        ) {
            tabList.forEachIndexed { index, s ->
                Tab(selected = index == pageState.currentPage,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.LightGray,
                    interactionSource = MutableInteractionSource(),
                    modifier = Modifier.padding(12.dp), onClick = {
                        scope.launch {
                            pageState.animateScrollToPage(index)
                        }
                    }) {
                    Text(text = s, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        HorizontalPager(
            state = pageState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            if (page == 0) {
                ReadMessageList()
            } else {
                UnReadMessageList()
            }
        }
    }
}

@Composable
fun ReadMessageList(viewModel: MessageReadViewModel = hiltViewModel()) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseUiStateListPage(uiPageState = uiPageState,
        contentPadding = PaddingValues(12.dp),
        onRefresh = { viewModel.getReadMessageList(true) },
        onLoadMore = {
            viewModel.getReadMessageList(false)
        }) {
        MessageItemView(item = it) {

        }
    }
}

@Composable
fun UnReadMessageList(viewModel: MessageUnreadViewModel = hiltViewModel()) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseUiStateListPage(
        uiPageState = uiPageState,
        contentPadding = PaddingValues(12.dp),
        onRefresh = { viewModel.getMessageList(true) },
        onLoadMore = {
            viewModel.getMessageList(false)
        }) {
        MessageItemView(item = it) {

        }
    }
}

@Composable
private fun MessageItemView(
    item: MessageDTO,
    onMessageItemClick: (MessageDTO) -> Unit
) {
    Card(
        onClick = { onMessageItemClick.invoke(item) },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()


    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.tag, color = Color.Green, fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ),
                    ),
                    modifier = Modifier
                        .border(1.dp, color = Color.Green, shape = RoundedCornerShape(3.dp))
                        .padding(top = 4.dp, bottom = 4.dp, start = 5.dp, end = 5.dp)
                )

                Text(text = item.niceDate, fontSize = 12.sp, color = Color.DarkGray)
            }

            HorizontalSpace(height = 12.dp)
            Text(

                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            fontSize = 16.sp
                        )
                    ) {
                        append("@${item.fromUser}")
                    }
                    append(item.title)
                },
                fontSize = 14.sp,
                color = Color.Black
            )
            HorizontalSpace(height = 10.dp)
            Text(text = item.message, fontSize = 13.sp, color = Color.DarkGray)
        }
    }
}