package com.zhq.commonlib.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhq.commonlib.R

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/7 18:09
 * Description
 */
@Composable
fun CommonEmptyState(
    msg: String = "暂无数据"
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp),
                painter = painterResource(id = R.drawable.ic_common_no_data),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = msg, fontSize = 13.sp, color = Color(0xff262626))
        }
    }
}