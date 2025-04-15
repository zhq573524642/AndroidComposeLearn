package com.zhq.jetpackcomposelearn.ui.screen.mine.collect

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseRefreshContainer
import com.zhq.commonlib.base.widgets.BaseUiStatePage
import com.zhq.commonlib.utils.ColorUtils
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.NavigationsDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 10:15
 * Description
 */

@Composable
fun NavigationFunctionView(
    viewModel: NavigationFuncViewModel = hiltViewModel(),
    onNavigationItemClick: (ArticleDTO) -> Unit
) {

    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseUiStatePage(uiPageState = uiPageState,
        onRefresh = {
            viewModel.getNavigationData(true)
        }) { list: List<NavigationsDTO> ->
        FlowLayoutView(list = list) {
            onNavigationItemClick.invoke(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
private fun FlowLayoutView(
    list: List<NavigationsDTO>,
    onNavigationItemClick: (ArticleDTO) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        list.forEachIndexed { index: Int, item: NavigationsDTO ->
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xfff5f5f5))
                        .padding(top = 5.dp, bottom = 5.dp)
                ) {
                    Text(
                        text = item.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

            }

            item {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = 5
                ) {
                    item.articles.forEach { childItem ->
                        Text(
                            text = childItem.title,
                            color = Color(ColorUtils.generateRandomColorLong()),
                            fontSize = 14.sp,
                            modifier = Modifier.clickable {
                                onNavigationItemClick.invoke(childItem)
                            }
                        )
                    }
                }
            }
        }
    }
} 