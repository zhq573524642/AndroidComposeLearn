package com.zhq.jetpackcomposelearn.navi

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zhq.commonlib.utils.ToastUtils.showCenterToast
import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.ui.screen.harmony.HarmonyScreen
import com.zhq.jetpackcomposelearn.ui.screen.home.HomeScreen
import com.zhq.jetpackcomposelearn.ui.screen.login.LoginScreen
import com.zhq.jetpackcomposelearn.ui.screen.main.MainScreen
import com.zhq.jetpackcomposelearn.ui.screen.mine.MineScreen
import com.zhq.jetpackcomposelearn.ui.screen.projects.ProjectsScreen
import com.zhq.jetpackcomposelearn.ui.screen.questions.QuestionsScreen
import com.zhq.jetpackcomposelearn.ui.screen.search.SearchScreen


/**
 * @Author ZhangHuiQiang
 * @Date 2024/12/12 14:11
 * Description  Navigation 导航图
 */

@Composable
fun NavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {

    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navHostController,
        startDestination = PageRoute.Home.name
    ) {
        composable(PageRoute.Main.name) {
            MainScreen(navHostController = navHostController)
        }
        composable(PageRoute.Home.name) {
            HomeScreen(
                onSearchClick = {
                    navHostController.navigate(PageRoute.SearchPage.name)
                },
                onMessageClick = {
                    navHostController.navigate(PageRoute.LoginScreen.name)
                },
                onBannerItemClick = {

                }
            ) { article: ArticleDTO ->
                article.title.showCenterToast()
            }
        }
        composable(PageRoute.Harmony.name) {
            HarmonyScreen()
        }
        composable(PageRoute.Projects.name) {
            ProjectsScreen()
        }
        composable(PageRoute.Questions.name) {
            QuestionsScreen {

            }
        }
        composable(PageRoute.Mine.name) {
            if (UserManager.isLogin()) {
                MineScreen()
            } else {
                LoginScreen(onClosePage = { navHostController.popBackStack() }) {
                    navHostController.popBackStack()
                }
            }
        }
        composable(PageRoute.SearchPage.name) {
            SearchScreen()
        }

        composable(PageRoute.LoginScreen.name) {
            LoginScreen(onClosePage = {
                navHostController.popBackStack()
            }) {
                navHostController.popBackStack()
            }
        }


    }
}

enum class PageRoute {
    Main,
    Home,
    Harmony,
    Projects,
    Questions,
    Mine,
    SearchPage,
    LoginScreen
}