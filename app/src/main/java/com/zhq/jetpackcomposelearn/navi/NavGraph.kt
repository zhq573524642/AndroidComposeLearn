package com.zhq.jetpackcomposelearn.navi

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.zhq.commonlib.utils.ToastUtils.showCenterToast
import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.ui.screen.course.CourseCatalogRoute
import com.zhq.jetpackcomposelearn.ui.screen.course.CourseCatalogScreen
import com.zhq.jetpackcomposelearn.ui.screen.course.CoursesRoute
import com.zhq.jetpackcomposelearn.ui.screen.course.CoursesScreen
import com.zhq.jetpackcomposelearn.ui.screen.harmony.HarmonyScreen
import com.zhq.jetpackcomposelearn.ui.screen.home.HomeScreen
import com.zhq.jetpackcomposelearn.ui.screen.login.LoginScreen
import com.zhq.jetpackcomposelearn.ui.screen.main.MainScreen
import com.zhq.jetpackcomposelearn.ui.screen.mine.MineScreen
import com.zhq.jetpackcomposelearn.ui.screen.mine.setting.SettingRoute
import com.zhq.jetpackcomposelearn.ui.screen.mine.setting.SettingScreen
import com.zhq.jetpackcomposelearn.ui.screen.projects.ProjectsScreen
import com.zhq.jetpackcomposelearn.ui.screen.questions.QuestionsScreen
import com.zhq.jetpackcomposelearn.ui.screen.search.SearchScreen
import com.zhq.jetpackcomposelearn.ui.screen.systems.SystemChildListRoute
import com.zhq.jetpackcomposelearn.ui.screen.systems.SystemsChildListScreen
import com.zhq.jetpackcomposelearn.ui.screen.systems.SystemsRoute
import com.zhq.jetpackcomposelearn.ui.screen.systems.SystemsScreen
import com.zhq.jetpackcomposelearn.ui.screen.web.WebViewRoute
import com.zhq.jetpackcomposelearn.ui.screen.web.WebViewScreen


/**
 * @Author ZhangHuiQiang
 * @Date 2024/12/12 14:11
 * Description  Navigation 导航图
 */

@Composable
fun NavGraph(
    navHostController: NavHostController,
) {

    NavHost(
        navController = navHostController,
        startDestination = PageRoute.Home.name
    ) {
        //主页
        composable(PageRoute.Main.name) {
            MainScreen(navHostController = navHostController)
        }
        //首页
        composable(PageRoute.Home.name) {
            HomeScreen(
                onSearchClick = {//首页搜索
                    navHostController.navigate(PageRoute.SearchPage.name)
                },
                onMessageClick = {//首页消息
                    navHostController.navigate(PageRoute.LoginScreen.name)
                },
                onBannerItemClick = {//首页Banner

                }
            ) { article: ArticleDTO ->//首页文章列表点击跳转
                navHostController.navigate(WebViewRoute(article.title, article.link))
            }
        }
        //鸿蒙模块
        composable(PageRoute.Harmony.name) {
            HarmonyScreen(onItemClick = { title, link ->
                navHostController.navigate(WebViewRoute(title = title, url = link))
            })
        }
        //项目模块
        composable(PageRoute.Projects.name) {
            ProjectsScreen(
                onSystemsClick = {//体系
                    navHostController.navigate(SystemsRoute)
                },
                onCoursesClick = {//教程
                    navHostController.navigate(CoursesRoute)
                },
                onProjectItemClick = { item: ArticleDTO ->//项目列表点击
                    navHostController.navigate(WebViewRoute(title = item.title, url = item.link))
                })
        }
        //问答模块
        composable(PageRoute.Questions.name) {
            QuestionsScreen(
                onSquareClick = {

                }
            ) { item: ArticleDTO ->
                navHostController.navigate(WebViewRoute(item.title, item.link))
            }
        }
        //我的模块
        composable(PageRoute.Mine.name) {
            MineScreen(
                onLoginClick = {
                    navHostController.navigate(PageRoute.LoginScreen.name)
                },
                onTodoClick = {},
                onIntegralClick = {},
                onSettingClick = {
                    navHostController.navigate(SettingRoute)
                }
            )

        }
        //搜索
        composable(PageRoute.SearchPage.name) {
            SearchScreen()
        }
        //登录模块
        composable(PageRoute.LoginScreen.name) {
            LoginScreen(onClosePage = {
                navHostController.popBackStack()
            }) {
                navHostController.popBackStack()
            }
        }
        //WebView
        composable<WebViewRoute> { navBackStackEntry: NavBackStackEntry ->
            val route: WebViewRoute = navBackStackEntry.toRoute()
            WebViewScreen(route = route, navHostController = navHostController)
        }
        //体系
        composable<SystemsRoute> { navBackStackEntry: NavBackStackEntry ->
            val route: SystemsRoute = navBackStackEntry.toRoute()
            SystemsScreen(
                route = route,
                navHostController = navHostController
            ) { name: String, id: Int ->
                navHostController.navigate(SystemChildListRoute(name, id))
            }
        }
        //体系对应列表
        composable<SystemChildListRoute> { navBackStackEntry: NavBackStackEntry ->
            val route: SystemChildListRoute = navBackStackEntry.toRoute()
            SystemsChildListScreen(route = route, navHostController = navHostController,
                onAuthorClick = { item: ArticleDTO ->

                }) { item: ArticleDTO ->
                navHostController.navigate(WebViewRoute(item.title, item.link))
            }
        }
        //教程
        composable<CoursesRoute> { navBackStackEntry: NavBackStackEntry ->
            val route: CoursesRoute = navBackStackEntry.toRoute()
            CoursesScreen(
                route = route,
                navHostController = navHostController
            ) { item: ArticleDTO ->
                navHostController.navigate(CourseCatalogRoute(item.name, item.id))
            }
        }

        //教程目录
        composable<CourseCatalogRoute> { navBackStackEntry: NavBackStackEntry ->
            val route: CourseCatalogRoute = navBackStackEntry.toRoute()
            CourseCatalogScreen(route = route, navHostController = navHostController) {
                navHostController.navigate(WebViewRoute(it.title, it.link))
            }
        }

        //设置
        composable<SettingRoute> { navBackStackEntry: NavBackStackEntry ->
            val route: SettingRoute = navBackStackEntry.toRoute()
            SettingScreen(navHostController = navHostController) {
                navHostController.navigate(PageRoute.LoginScreen.name)
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
    LoginScreen,
    WebView,
}