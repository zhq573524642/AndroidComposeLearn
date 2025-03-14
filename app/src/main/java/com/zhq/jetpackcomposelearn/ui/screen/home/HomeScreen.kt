package com.zhq.jetpackcomposelearn.ui.screen.home

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.widgets.Banner
import com.zhq.jetpackcomposelearn.common.BigTitleHeader
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.BannerDTO
import com.zhq.jetpackcomposelearn.ui.screen.ArticleRefreshList
import com.zhq.jetpackcomposelearn.ui.screen.articles.ArticleItem

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 14:24
 * Description
 */
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onSearchClick: () -> Unit,
    onMessageClick: () -> Unit,
    onBannerItemClick: (BannerDTO) -> Unit = {},
    onArticleItemClick: (ArticleDTO) -> Unit
) {
    val bannerData by homeViewModel.bannerList.collectAsState()
    Column {
        BigTitleHeader(title = "首页",
            actions = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "搜索",
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable {
                            onSearchClick.invoke()
                        })
                Icon(imageVector = Icons.Default.Email, contentDescription = "消息",
                    modifier = Modifier
                        .clickable {
                            onMessageClick.invoke()
                        })
            })

        ArticleRefreshList(
            viewModel = homeViewModel,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
            lazyListState = rememberLazyListState(),
            onRefresh = {
                homeViewModel.getBannerData()
                homeViewModel.getHomeArticleList()
            },
            onLoadMore = {
                homeViewModel.getHomeArticleList(false)
            },
            headerContent = {
                if (bannerData.isNotEmpty()) {
                    Banner(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 12.dp, bottom = 12.dp),
                        images = bannerData.map { it.imagePath }) {
                        onBannerItemClick(bannerData[it])
                    }

                }

            }
        ) {
            ArticleItem(
                item = it,
                articleViewModel = homeViewModel,
                articleItemClick = onArticleItemClick
            )
        }
    }
}