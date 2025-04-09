package com.zhq.jetpackcomposelearn.navi

import android.app.Activity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zhq.jetpackcomposelearn.ext.decorFitsSystemWindows
import com.zhq.jetpackcomposelearn.ui.screen.main.MainScreen
import com.zhq.jetpackcomposelearn.ui.screen.splash.SplashScreen
import com.zhq.jetpackcomposelearn.ui.theme.JetpackComposeLerarnTheme

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 17:03
 * Description
 */

@Composable
fun AppScreen(
    navHostController: NavHostController,
    appScreenViewModel: AppScreenViewModel = hiltViewModel()
) {
    val isShowSplash by appScreenViewModel.isFirstOpen.collectAsState()
    JetpackComposeLerarnTheme{
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false // 初始图标颜色
        )

        if (isShowSplash) {
            //显示Splash
            SplashScreen {
                appScreenViewModel.emitFirstUse(false)
            }
        } else {
            MainScreen(navHostController = navHostController)
        }
    }

}