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
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.ToolsDTO
import com.zhq.jetpackcomposelearn.ui.screen.articles.ArticleRoute
import com.zhq.jetpackcomposelearn.ui.screen.articles.ArticlesScreen
import com.zhq.jetpackcomposelearn.ui.screen.articles.FromEnum
import com.zhq.jetpackcomposelearn.ui.screen.mine.coin.CoinRoute
import com.zhq.jetpackcomposelearn.ui.screen.mine.coin.CoinScreen
import com.zhq.jetpackcomposelearn.ui.screen.mine.coin.GetCoinRoute
import com.zhq.jetpackcomposelearn.ui.screen.mine.coin.MyGetCoinScreen
import com.zhq.jetpackcomposelearn.ui.screen.course.CourseCatalogRoute
import com.zhq.jetpackcomposelearn.ui.screen.course.CourseCatalogScreen
import com.zhq.jetpackcomposelearn.ui.screen.course.CoursesRoute
import com.zhq.jetpackcomposelearn.ui.screen.course.CoursesScreen
import com.zhq.jetpackcomposelearn.ui.screen.harmony.HarmonyScreen
import com.zhq.jetpackcomposelearn.ui.screen.home.HomeScreen
import com.zhq.jetpackcomposelearn.ui.screen.login.LoginScreen
import com.zhq.jetpackcomposelearn.ui.screen.main.MainScreen
import com.zhq.jetpackcomposelearn.ui.screen.message.MessageRoute
import com.zhq.jetpackcomposelearn.ui.screen.message.MessageScreen
import com.zhq.jetpackcomposelearn.ui.screen.mine.MineScreen
import com.zhq.jetpackcomposelearn.ui.screen.mine.setting.ArtifactRoute
import com.zhq.jetpackcomposelearn.ui.screen.mine.setting.ArtifactScreen
import com.zhq.jetpackcomposelearn.ui.screen.mine.setting.GoogleMavenPackageRoute
import com.zhq.jetpackcomposelearn.ui.screen.mine.setting.GoogleMavenScreen
import com.zhq.jetpackcomposelearn.ui.screen.mine.setting.SettingRoute
import com.zhq.jetpackcomposelearn.ui.screen.mine.setting.SettingScreen
import com.zhq.jetpackcomposelearn.ui.screen.mine.setting.ToolsRoute
import com.zhq.jetpackcomposelearn.ui.screen.mine.setting.ToolsScreen
import com.zhq.jetpackcomposelearn.ui.screen.mine.todo.TodoRoute
import com.zhq.jetpackcomposelearn.ui.screen.mine.todo.TodoScreen
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
                    if (UserManager.isLogin()) {
                        navHostController.navigate(MessageRoute)
                    } else {
                        navHostController.navigate(PageRoute.LoginScreen.name)
                    }
                },
                onBannerItemClick = {//首页Banner
                   navHostController.navigate(WebViewRoute(title = it.title, url = it.url))
                },
                onAuthorClick = { item: ArticleDTO, isAuthor: Boolean ->
                    if (isAuthor) {
                        navHostController.navigate(
                            ArticleRoute(
                                FromEnum.authorArticleList,
                                title = "作者：${item.author}",
                                author = item.author
                            )
                        )
                    } else {
                        navHostController.navigate(
                            ArticleRoute(
                                FromEnum.shareUserArticleList,
                                title = "分享者：${item.shareUser}",
                                id = item.userId
                            )
                        )
                    }
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
                    navHostController.navigate(
                        ArticleRoute(
                            from = FromEnum.squareArticleList,
                            title = "广场"
                        )
                    )
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
                onMyShare = {
                    navHostController.navigate(
                        ArticleRoute(
                            from = FromEnum.myShareArticleList,
                            title = "我的分享"
                        )
                    )
                },
                onTodoClick = {
                    navHostController.navigate(TodoRoute)
                },
                onIntegralClick = {
                    navHostController.navigate(CoinRoute)
                },
                onSettingClick = {
                    navHostController.navigate(SettingRoute)
                },
                onMyCollectArticleItemClick = {
                    navHostController.navigate(WebViewRoute(title = it.title, url = it.link))
                },
                onWebsiteItemClick = {
                    navHostController.navigate(WebViewRoute(title = it.name, url = it.link))
                },
                onNavigationItemClick = {
                    navHostController.navigate(WebViewRoute(title = it.title, url = it.link))
                },
                onOfficialAccountClick = {
                    navHostController.navigate(
                        ArticleRoute(
                            FromEnum.officialAccountHistory,
                            it.name,
                            it.id
                        )
                    )
                }
            )

        }
        //搜索
        composable(PageRoute.SearchPage.name) {
            SearchScreen(
                onBackClick = {
                    navHostController.popBackStack()
                },
                onSearchResultItemClick = {
                    navHostController.navigate(
                        WebViewRoute(title = it.title, url = it.link)
                    )
                },
                onAuthorClick = { article: ArticleDTO, isAuthor: Boolean ->

                    if (isAuthor) {
                        navHostController.navigate(
                            ArticleRoute(
                                FromEnum.authorArticleList,
                                title = "作者：${article.author}",
                                author = article.author
                            )
                        )
                    } else {
                        navHostController.navigate(
                            ArticleRoute(
                                from = FromEnum.shareUserArticleList,
                                title = "分享人:${article.shareUser}",
                                id = article.userId
                            )
                        )
                    }
                },
                onCommonWebsiteItemClick = {
                    navHostController.navigate(
                        WebViewRoute(
                            title = it.name, url = it.link
                        )
                    )
                })
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
            SettingScreen(navHostController = navHostController,
                onToolsClick = {
                    navHostController.navigate(ToolsRoute)
                },
                onGoogleMavenClick = {
                    navHostController.navigate(GoogleMavenPackageRoute)
                }) {
                App.appViewModel.emitUser(null)
                UserManager.saveUser(null)
                navHostController.navigate(PageRoute.LoginScreen.name)
            }
        }
        //消息
        composable<MessageRoute> { navBackStackEntry: NavBackStackEntry ->
            val route: MessageRoute = navBackStackEntry.toRoute()
            MessageScreen(navHostController = navHostController)
        }

        composable<ArticleRoute> { navBackStackEntry: NavBackStackEntry ->
            val route: ArticleRoute = navBackStackEntry.toRoute()
            ArticlesScreen(route = route, onScreenBack = { navHostController.popBackStack() },
                onAuthorClick = { article: ArticleDTO, isAuthor: Boolean ->
                    if (isAuthor) {
                        navHostController.navigate(
                            ArticleRoute(
                                FromEnum.authorArticleList,
                                title = "作者：${article.author}",
                                author = article.author
                            )
                        )
                    } else {
                        navHostController.navigate(
                            ArticleRoute(
                                from = FromEnum.shareUserArticleList,
                                title = "分享人:${article.shareUser}",
                                id = article.userId
                            )
                        )
                    }
                },
                onArticleItemClick = { articleDTO: ArticleDTO ->
                    navHostController.navigate(
                        WebViewRoute(
                            title = articleDTO.title,
                            url = articleDTO.link
                        )
                    )
                })
        }

        composable<CoinRoute> { navBackStackEntry: NavBackStackEntry ->
            val route: CoinRoute = navBackStackEntry.toRoute()
            CoinScreen(
                onBackClick = {
                    navHostController.popBackStack()
                }
            ) {
                navHostController.navigate(GetCoinRoute)
            }
        }
        composable<GetCoinRoute> { navBackStackEntry: NavBackStackEntry ->
            MyGetCoinScreen(
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }

        composable<ToolsRoute> { navBackStackEntry: NavBackStackEntry ->
            ToolsScreen(
                onBackClick = {
                    navHostController.popBackStack()
                }
            ) { item: ToolsDTO ->
                navHostController.navigate(WebViewRoute(title = item.name, url = item.link))
            }
        }

        composable<GoogleMavenPackageRoute> {
            GoogleMavenScreen(onBackClick = { navHostController.popBackStack() }) { item: String ->
                navHostController.navigate(ArtifactRoute(key = item))
            }
        }

        composable<ArtifactRoute> { navBackStackEntry: NavBackStackEntry ->
            val route: ArtifactRoute = navBackStackEntry.toRoute()
            ArtifactScreen(route = route,
                onBackClick = {
                    navHostController.popBackStack()
                })
        }

        composable<TodoRoute> { navBackStackEntry: NavBackStackEntry ->
            TodoScreen {
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
    LoginScreen,
    WebView,
}