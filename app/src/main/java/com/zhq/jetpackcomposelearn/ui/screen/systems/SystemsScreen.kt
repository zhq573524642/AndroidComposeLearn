@file:OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)

package com.zhq.jetpackcomposelearn.ui.screen.systems

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zhq.commonlib.base.widgets.BaseRefreshContainer
import com.zhq.commonlib.utils.ColorUtils
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.ui.screen.systems.model.SystemsChildDTO
import com.zhq.jetpackcomposelearn.ui.screen.systems.model.SystemsDTO
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/7 17:15
 * Description
 */

@Serializable
data object SystemsRoute

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SystemsScreen(
    viewModel: SystemsViewModel = hiltViewModel(),
    route: SystemsRoute,
    navHostController: NavHostController,
    onSystemsItemClick: (String, Int) -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseRefreshContainer(
        topAppBar = {
            CenterTitleHeader(title = "体系", onBack = { navHostController.popBackStack() })
        },
        uiPageState = uiPageState,
        isAutoRefresh = false,
        onRefresh = {
            viewModel.getSystemsList(true)
        }) { list: List<SystemsDTO> ->
        FlowLayout(list = list,
            onSystemsItemClick = { name: String, id: Int ->
                onSystemsItemClick.invoke(name, id)
            })

    }
}

@Composable
fun FlowLayout(
    list: List<SystemsDTO>,
    onSystemsItemClick: (String, Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        list.forEach { item: SystemsDTO ->
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color(0xfff5f5f5)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            item {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),//主轴上排列方式
                    verticalArrangement = Arrangement.spacedBy(8.dp),//交叉轴方向间距，其他不生效
                    maxItemsInEachRow = 4//最多显示3列
                ) {
                    item.children.forEach { child: SystemsChildDTO ->
                        Text(
                            text = child.name,
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    color = Color(ColorUtils.generateRandomColorLong()),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
                                .clickable {
                                    onSystemsItemClick.invoke(child.name, child.id)
                                }
                        )
                    }
                }
            }


        }
    }
}