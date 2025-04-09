package com.zhq.jetpackcomposelearn

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zhq.commonlib.base.BaseActivity
import com.zhq.jetpackcomposelearn.navi.AppScreen
import com.zhq.jetpackcomposelearn.ui.theme.JetpackComposeLerarnTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            AppScreen(navHostController = navHostController)
        }
    }

}
