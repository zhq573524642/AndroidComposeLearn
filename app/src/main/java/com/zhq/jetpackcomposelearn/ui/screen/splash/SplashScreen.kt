package com.zhq.jetpackcomposelearn.ui.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 17:31
 * Description
 */

@Composable
fun SplashScreen(onSplashEnd: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { onSplashEnd() }) {
            Text(text = "点击进入")
        }
    }
}