package com.zhq.commonlib.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 17:14
 * Description
 */
@Composable
fun NoMoreDataView(
    msg: String = "暂无更多数据"
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = msg,
            color = Color.DarkGray,
            fontSize = 14.sp
        )
    }
}