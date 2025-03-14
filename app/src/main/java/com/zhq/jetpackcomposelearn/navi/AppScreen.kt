package com.zhq.jetpackcomposelearn.navi

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
    val window = (LocalContext.current as? Activity)?.window
    val isShowSplash by appScreenViewModel.isFirstOpen.collectAsState()

    JetpackComposeLerarnTheme(isStatusBarTransparent = isShowSplash) {
        if (isShowSplash) {
            //显示Splash
            window?.decorFitsSystemWindows(true)
            SplashScreen {
                appScreenViewModel.emitFirstUse(false)
            }
        } else {
            window?.decorFitsSystemWindows(false)
            MainScreen(navHostController = navHostController)
        }
    }

}