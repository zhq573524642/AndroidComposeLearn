package com.zhq.jetpackcomposelearn.ui.screen.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.OfficialAccountDTO
import com.zhq.jetpackcomposelearn.ui.screen.mine.collect.CollectArticleView
import com.zhq.jetpackcomposelearn.ui.screen.mine.collect.CollectWebsiteView
import com.zhq.jetpackcomposelearn.ui.screen.mine.collect.NavigationFunctionView
import com.zhq.jetpackcomposelearn.ui.screen.mine.collect.OfficialAccountsView
import kotlinx.coroutines.launch

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/11 11:40
 * Description
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MintTabPager(
    onArticleItemClick: (ArticleDTO) -> Unit,
    onWebsiteItemClick: (ArticleDTO) -> Unit,
    onNavigationItemClick: (ArticleDTO) -> Unit,
    onOfficialAccountClick: (OfficialAccountDTO) -> Unit
) {
    val tabList = listOf("收藏文章", "收藏网站", "导航", "公众号")
    val pagerState = rememberPagerState(initialPage = 0) {
        tabList.size
    }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()

    ) {
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
            divider = {},
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        pagerState.currentPage,
                        matchContentSize = true
                    ),
                    color = Color.Black,
                    width = Dp.Unspecified,
                )
            }
        ) {
            tabList.forEachIndexed { index, item ->
                Tab(selected = index == pagerState.currentPage,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.LightGray,
                    interactionSource = MutableInteractionSource(),
                    modifier = Modifier.padding(10.dp), onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }) {
                    Text(text = item, fontSize = 15.sp)
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfff5f5f5)),
        ) { page: Int ->
            when (page) {
                0 -> CollectArticleView {
                    onArticleItemClick.invoke(it)
                }

                1 -> CollectWebsiteView {
                    onWebsiteItemClick.invoke(it)
                }

                2 -> NavigationFunctionView {
                    onNavigationItemClick.invoke(it)
                }

                3 -> OfficialAccountsView {
                    onOfficialAccountClick.invoke(it)
                }
            }

        }
    }
}