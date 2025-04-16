package com.zhq.jetpackcomposelearn.ui.screen.mine.coin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.common.VerticalSpace
import com.zhq.jetpackcomposelearn.data.CoinInfoDTO
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 14:57
 * Description
 */

@Serializable
data object CoinRoute

@Composable
fun CoinScreen(
    viewModel: CoinViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onMyGetCoinRecordClick: () -> Unit
) {

    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseRefreshListContainer(uiPageState = uiPageState,
        topAppBar = {
            CenterTitleHeader(title = "积分", onBack = { onBackClick.invoke() },
                rightActions = {
                    Text(text = "我的积分获取记录", color = Color.Blue,
                        modifier = Modifier.clickable {
                            onMyGetCoinRecordClick.invoke()
                        })
                })
        },
        onRefresh = {
            viewModel.getMyCoinInfo()
            viewModel.getUserCoinRankList(true)
        },
        onLoadMore = {
            viewModel.getUserCoinRankList(false)
        },
        contentPadding = PaddingValues(12.dp),
        isHeaderScrollWithList = false,
        headerContent = {
            MyCoinInfo(viewModel)
        }
    ) { item: CoinInfoDTO ->
        UserCoinInfo(item = item)
    }
}

@Composable
fun MyCoinInfo(viewModel: CoinViewModel) {
    val coinInfo by viewModel.myCoinInfo.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            buildAnnotatedString {
                append("我的积分：")
                withStyle(
                    style = SpanStyle(
                        color = Color.Yellow,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(coinInfo?.coinCount)
                }
            },
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Text(
            buildAnnotatedString {
                append("排名：")
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("${coinInfo?.rank}")
                }
            },
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
private fun UserCoinInfo(item: CoinInfoDTO) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${item.rank}",
                color = when (item.rank) {
                    1 -> Color.Yellow
                    2 -> Color.Red
                    3 -> Color.Green
                    else -> Color.Black
                },
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            VerticalSpace(width = 10.dp)
            Text(
                text = item.username,
                color = Color.Black,
                fontSize = 16.sp
            )
            VerticalSpace(width = 5.dp)
            Text(
                text = "lv:${item.level}",
                color = Color.Green,
                fontSize = 12.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_integral_coin),
                tint = Color.Yellow,
                modifier = Modifier.size(12.dp), contentDescription = ""
            )
            Text(
                text = item.coinCount,
                fontSize = 13.sp,
                color = Color.Yellow
            )
        }
    }
}