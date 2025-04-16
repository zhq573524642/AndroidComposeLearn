package com.zhq.jetpackcomposelearn.ui.screen.mine.coin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.data.GetCoinDTO
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 15:37
 * Description
 */
@Serializable
data object GetCoinRoute

@Composable
fun MyGetCoinScreen(
    viewModel: GetCoinViewModel = hiltViewModel(),
    onBackClick:()->Unit

) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseRefreshListContainer(uiPageState = uiPageState,
        topAppBar = {
            CenterTitleHeader(title = "积分获取记录", onBack = {onBackClick.invoke() })
        },
        contentPadding = PaddingValues(12.dp),
        onRefresh = { viewModel.getGetCoinRecordList(true) },
        onLoadMore = {
            viewModel.getGetCoinRecordList(false)
        }) { item: GetCoinDTO ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.desc, color = Color.Black, fontSize = 13.sp)
            Text(text = "获得:${item.coinCount}", color = Color.Yellow, fontSize = 12.sp)
        }
    }
}