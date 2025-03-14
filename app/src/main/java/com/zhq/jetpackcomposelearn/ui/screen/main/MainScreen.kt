package com.zhq.jetpackcomposelearn.ui.screen.main

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.navi.NavGraph
import com.zhq.jetpackcomposelearn.navi.PageRoute

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 17:40
 * Description
 */

@Composable
fun MainScreen(navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {

            if (destination?.hierarchy?.any {
                    navigationItemList.map { navBarItem -> navBarItem.route }
                        .contains(it.route)
                } == true) {
                BottomNavigationBar(navHostController, destination)
            }
        }
    ) { innerPadding ->
        NavGraph(navHostController = navHostController, paddingValues = innerPadding)
    }
}


val navigationItemList = listOf(
    NavigationItem.HOME,
    NavigationItem.HMOS,
    NavigationItem.PROJECTS,
    NavigationItem.QUESTIONS,
    NavigationItem.MINE
)


@Composable
fun BottomNavigationBar(
    navHostController: NavHostController,
    navDestination: NavDestination?
) {

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = Color.White
    ) {
        navigationItemList.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = navDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    if (item.route == NavigationItem.HMOS.route) {
                        navHostController.navigate(item.route + "?userId=1212/张三") {
                            // 这里让多个Tab下返回时，不是回到首页，而是直接退出
                            navDestination?.id?.let {
                                popUpTo(it) {
                                    // 跳转时保存页面状态
                                    saveState = true
                                    // 回退到栈顶时，栈顶页面是否也关闭
                                    inclusive = true
                                }
                            }
                            // 栈顶复用，避免重复点击同一个导航按钮，回退栈中多次创建实例
                            launchSingleTop = true
                            // 回退时恢复页面状态
                            restoreState = true
                        }
                    } else {
                        navHostController.navigate(item.route) {
                            // 这里让多个Tab下返回时，不是回到首页，而是直接退出
                            navDestination?.id?.let {
                                popUpTo(it) {
                                    // 跳转时保存页面状态
                                    saveState = true
                                    // 回退到栈顶时，栈顶页面是否也关闭
                                    inclusive = true
                                }
                            }
                            // 栈顶复用，避免重复点击同一个导航按钮，回退栈中多次创建实例
                            launchSingleTop = true
                            // 回退时恢复页面状态
                            restoreState = true
                        }
                    }

                }, icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.route,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(bottom = 4.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 13.sp
                    )

                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xff515151),
                    selectedTextColor = Color(0xff515151),
                    unselectedIconColor = Color(0xffdbdbdb),
                    unselectedTextColor = Color(0xffdbdbdb)
                )
            )
        }
    }
}


sealed class NavigationItem(val title: String, val icon: Int, val route: String) {
    data object HOME : NavigationItem("首页", R.drawable.ic_tab_home, PageRoute.Home.name)
    data object HMOS : NavigationItem("鸿蒙", R.drawable.ic_tab_hw, PageRoute.Harmony.name)
    data object PROJECTS :
        NavigationItem("项目", R.drawable.ic_tab_project, PageRoute.Projects.name)

    data object QUESTIONS :
        NavigationItem("问答", R.drawable.ic_tab_question, PageRoute.Questions.name)

    data object MINE : NavigationItem("我的", R.drawable.ic_tab_mine, PageRoute.Mine.name)
}

