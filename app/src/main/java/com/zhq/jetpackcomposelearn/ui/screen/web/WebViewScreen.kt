@file:OptIn(ExperimentalMaterial3Api::class)

package com.zhq.jetpackcomposelearn.ui.screen.web

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.zhq.jetpackcomposelearn.common.BaseScreen
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.ui.theme.JetpackComposeLerarnTheme
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/2 14:06
 * Description
 */
@Serializable
data class WebViewRoute(val title:String,val url: String)

@Composable
fun WebViewScreen(
    route: WebViewRoute,
    navHostController: NavHostController
) {
    BaseScreen(title = route.title, onBack = { navHostController.popBackStack() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            AndroidView(factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    webChromeClient = WebChromeClient()
                    settings.javaScriptEnabled = true
                    loadUrl(route.url)
                }
            })
        }
    }



}