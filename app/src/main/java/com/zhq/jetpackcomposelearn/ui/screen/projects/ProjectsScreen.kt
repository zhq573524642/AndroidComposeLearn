package com.zhq.jetpackcomposelearn.ui.screen.projects

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.common.BigTitleHeader
import com.zhq.jetpackcomposelearn.data.ArticleDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 14:25
 * Description
 */
private const val TAG = "ProjectsScreen"
@Composable
fun ProjectsScreen(viewModel: ProjectsViewModel = hiltViewModel()) {

    val tabDataResult = viewModel.tabsState.value
    val tabCount = viewModel.tabCount
    val selectedIndex = viewModel.selectedIndex
    val pageState = rememberPagerState(initialPage = selectedIndex) {
        tabCount
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BigTitleHeader(title = "热门项目",
            actions = {

                Icon(
                    modifier = Modifier.size(23.dp),
                    painter = painterResource(id = R.drawable.ic_systems_icon),
                    contentDescription = "体系"
                )
                Icon(
                    modifier = Modifier.size(23.dp),
                    painter = painterResource(id = R.drawable.ic_course),
                    contentDescription = "教程"
                )

            })
        when (tabDataResult) {
            is DataResults.Loading -> {
                Log.d(TAG, "===ProjectsScreen: Loading")
            }
            is DataResults.Refreshing -> {}

            is DataResults.Error -> {
                Log.d(TAG, "===ProjectsScreen: ${tabDataResult.message}")
            }
            is DataResults.Success -> {
                val tabs = tabDataResult.data
                ScrollableTabRow(
                    selectedTabIndex = selectedIndex,
                    edgePadding = 0.dp
                ) {
                  tabs.forEachIndexed{index, articleDTO:ArticleDTO ->
                      Tab(selected = index == selectedIndex, onClick = { /*TODO*/ },
                          modifier = Modifier.padding(10.dp)) {
                          Text(text = articleDTO.name)
                      }
                  }
                }
            }
        }

    }
}