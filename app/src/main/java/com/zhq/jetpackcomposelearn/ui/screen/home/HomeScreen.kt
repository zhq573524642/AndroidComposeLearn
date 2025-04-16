package com.zhq.jetpackcomposelearn.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.commonlib.widgets.Banner
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.common.BigTitleHeader
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.BannerDTO
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
    onAuthorClick: (ArticleDTO, Boolean) -> Unit,
    onArticleItemClick: (ArticleDTO) -> Unit
) {
    val bannerData by homeViewModel.bannerList.collectAsState()
    val unreadMsgCount by homeViewModel.unreadCount.collectAsState()
    val uiPageState by homeViewModel.uiPageState.collectAsState()

    val collectData by App.appViewModel.collectEvent.observeAsState()
    val user by App.appViewModel.user.collectAsState()

    // 收藏事件监听
    if (collectData != null) {
        uiPageState.data?.forEach {
            if (it.id == collectData!!.id) {
                it.collect = collectData!!.collect
            }
        }
    }
    // 用户退出时，收藏应全为false，登录时获取collectIds
    if (user == null) {
        uiPageState.data?.forEach {
            it.collect = false
        }
    } else {
        uiPageState.data?.forEach {
            user?.userInfo?.collectIds?.forEach { id ->
                if (id == it.id) {
                    it.collect = true
                }
            }
        }
    }
    BaseRefreshListContainer(
        uiPageState = uiPageState,
        lazyListState = rememberLazyListState(),
        contentPadding = PaddingValues(12.dp),
        itemSpace = 12.dp,
        topAppBar = {
            BigTitleHeader(title = "首页",
                actions = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "搜索",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable {
                                onSearchClick.invoke()
                            })
                    if (unreadMsgCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ) {
                                    Text(
                                        text = "$unreadMsgCount",
                                        fontSize = 10.sp,
                                        color = Color.White
                                    )
                                }
                            }

                        ) {
                            Icon(imageVector = Icons.Default.Email, contentDescription = "消息",
                                modifier = Modifier
                                    .clickable {
                                        onMessageClick.invoke()
                                    })
                        }
                    } else {
                        Icon(imageVector = Icons.Default.Email, contentDescription = "消息",
                            modifier = Modifier
                                .clickable {
                                    onMessageClick.invoke()
                                })
                    }

                })
        },
        onRefresh = {
            if (UserManager.isLogin()) {
                homeViewModel.getUnreadMsgCount()
            }
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

        }) {
        ArticleItem(
            item = it,
            baseArticleViewModel = homeViewModel,
            onArticleItemClick = onArticleItemClick,
            onAuthorClick = { item: ArticleDTO, isAuthor: Boolean ->
                onAuthorClick.invoke(item, isAuthor)
            }
        )
    }

}