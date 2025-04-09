package com.zhq.jetpackcomposelearn.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 14:39
 * Description
 */
@Composable
fun VerticalSpace(width:Dp,bgColor:Color=Color.Transparent){
    Spacer(modifier = Modifier
        .width(width)
        .background(bgColor))
}