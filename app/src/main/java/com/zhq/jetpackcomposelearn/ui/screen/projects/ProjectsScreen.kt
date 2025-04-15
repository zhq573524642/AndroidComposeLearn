package com.zhq.jetpackcomposelearn.ui.screen.projects

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.data.model.DataResults
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.common.BigTitleHeader
import com.zhq.jetpackcomposelearn.common.DynamicStatusBarScreen
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.commonlib.ext.AutoLoadMoreHandler
import com.zhq.commonlib.widgets.CommonErrorState
import com.zhq.commonlib.widgets.CommonLoadingState
import com.zhq.commonlib.widgets.LoadErrorRetryView
import com.zhq.commonlib.widgets.LoadingMoreView
import com.zhq.commonlib.widgets.NoMoreDataView
import com.zhq.jetpackcomposelearn.App

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 14:25
 * Description
 */
private const val TAG = "ProjectsScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(
    viewModel: ProjectsViewModelBase = hiltViewModel(),
    onSystemsClick: () -> Unit,
    onCoursesClick: () -> Unit,
    onProjectItemClick: (ArticleDTO) -> Unit
) {

    val tabDataResult = viewModel.tabsState.value
    val tabCount = remember {
        mutableStateOf(0)
    }
    val selectedIndex = viewModel.selectedIndex
    val pageState = rememberPagerState(initialPage = selectedIndex) {
        tabCount.value
    }

    LaunchedEffect(pageState.currentPage) {
        viewModel.selectTab(pageState.currentPage)
    }

    LaunchedEffect(selectedIndex) {
        if (selectedIndex != pageState.currentPage) {
            pageState.animateScrollToPage(selectedIndex)
        }
    }

    DynamicStatusBarScreen(
        statusBarColor = Color(0xfff6f6f6),
        backgroundColor = Color(0xfff5f5f5)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BigTitleHeader(title = "热门项目",
                actions = {

                    Icon(
                        modifier = Modifier
                            .size(23.dp)
                            .clickable {
                                onSystemsClick.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_systems_icon),
                        contentDescription = "体系"
                    )
                    Icon(
                        modifier = Modifier
                            .size(23.dp)
                            .clickable {
                                onCoursesClick.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_course),
                        contentDescription = "教程"
                    )

                })
            when (tabDataResult) {
                is DataResults.Loading -> {
                    CommonLoadingState()
                }
                is DataResults.Refreshing -> {}

                is DataResults.Error -> {
                    CommonErrorState(
                        msg = tabDataResult.message
                    ) {
                        viewModel.loadTabData()
                    }

                }

                is DataResults.Success -> {
                    val tabs = tabDataResult.data
                    tabCount.value = tabs.size
                    ScrollableTabRow(
                        containerColor = Color(0xfff5f5f5),
                        selectedTabIndex = selectedIndex,
                        edgePadding = 0.dp,
                        divider = {},
                    ) {
                        tabs.forEachIndexed { index, articleDTO: ArticleDTO ->
                            Tab(
                                selected = index == selectedIndex,
                                onClick = { viewModel.selectTab(index) },
                                modifier = Modifier.padding(10.dp),
                                interactionSource = MutableInteractionSource()
                            ) {
                                Text(text = articleDTO.name)
                            }
                        }
                    }

                    HorizontalPager(
                        state = pageState,
                        modifier = Modifier.fillMaxSize()
                    ) { page: Int ->
                        val tab = tabs[page]
                        val tabPageState = viewModel.getPagerState(tabId = tab.id)
                        val lazyListState = rememberLazyListState()
                        val collectData by App.appViewModel.collectEvent.observeAsState()
                        val user by App.appViewModel.user.collectAsState()

                        // 收藏事件监听
                        if (collectData != null) {
                            tabPageState.pageData.datas.forEach {
                                if (it.id == collectData!!.id) {
                                    it.collect = collectData!!.collect
                                }
                            }
                        }
                        // 用户退出时，收藏应全为false，登录时获取collectIds
                        if (user == null) {
                            tabPageState.pageData.datas.forEach {
                                it.collect = false
                            }
                        } else {
                            tabPageState.pageData.datas.forEach {
                                user?.userInfo?.collectIds?.forEach { id ->
                                    if (id == it.id) {
                                        it.collect = true
                                    }
                                }
                            }
                        }
                        PullToRefreshBox(isRefreshing = tabPageState.isRefreshing, onRefresh = {
                            viewModel.refreshData(page)
                        }) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    start = 12.dp,
                                    end = 12.dp,
                                    top = 12.dp,
                                    bottom = 12.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                state = lazyListState
                            ) {
                                //内容条目
                                itemsIndexed(tabPageState.pageData.datas) { index: Int, item: ArticleDTO ->
                                    ProjectItem(item = item,
                                        viewModel) {
                                        onProjectItemClick.invoke(item)
                                    }

                                }
                                //加载更多
                                item {
                                    when {
                                        tabPageState.isLoadingMore -> LoadingMoreView(msg = "拼命加载中...")
                                        tabPageState.error != null -> LoadErrorRetryView(
                                            tabPageState.error
                                        ) { viewModel.loadPagerData(page, false) }

                                        tabPageState.pageData.curPage >= tabPageState.pageData.pageCount -> NoMoreDataView(msg = "—— 已经到底了 ——")
                                    }
                                }
                            }

                            // 添加自动加载检测（在LazyColumn外部）
                            AutoLoadMoreHandler(
                                listState = lazyListState,
                                buffer = 2,
                                onLoadMore = {
                                    viewModel.loadPagerData(page, false)
                                }
                            )
                        }
                    }
                }
            }

        }
    }


}

@Composable
private fun FullScreenLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun FullScreenError(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "错误：$message", color = Color.Red)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("重试")
        }
    }
}



