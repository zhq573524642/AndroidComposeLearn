package com.zhq.jetpackcomposelearn.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zhq.commonlib.ext.isLightColor

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 11:14
 * Description
 */
@Composable
fun BaseScreen(
    title:String,
    isFullScreen: Boolean = false,
    statusBarColor: Color = Color(0xfff5f5f5),
    backgroundColor: Color =  Color(0xfff5f5f5),
    onBack:()->Unit,
    content: @Composable () -> Unit
) {
    // 动态设置状态栏透明度
    val systemUiController = rememberSystemUiController()
    val statusColor = if (isFullScreen) Color.Transparent else statusBarColor

    systemUiController.setStatusBarColor(
        color = statusColor,
        darkIcons = if (isFullScreen) false else statusBarColor.isLightColor()
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            // 根据需求动态添加内边距
            .then(if (!isFullScreen) Modifier.statusBarsPadding() else Modifier)
            .then(if (isFullScreen) Modifier.systemGestureExclusion() else Modifier)
    ){
        CenterTitleHeader(title = title, onBack = { onBack.invoke() })
        content.invoke()
    }
}


