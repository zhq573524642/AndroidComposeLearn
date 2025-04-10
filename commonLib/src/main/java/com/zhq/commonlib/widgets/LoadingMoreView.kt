package com.zhq.commonlib.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/7 17:51
 * Description
 */
@Composable
fun LoadingMoreView(
    msg: String = "加载中..."
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(modifier = Modifier.size(30.dp))
            Text(text = msg, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(50.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            CircularProgressIndicator(modifier = Modifier.size(30.dp))
//            Text(text = msg, fontSize = 14.sp, color = Color.DarkGray)
//        }
//    }
}