package com.zhq.jetpackcomposelearn.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhq.jetpackcomposelearn.R

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/7 10:32
 * Description
 */

@Composable
fun BigTitleHeader(
    modifier: Modifier = Modifier,
    title: String,
    titleComposable: @Composable () -> Unit={},
    actions: @Composable RowScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 12.dp, top = 20.dp, bottom = 10.dp)
    ) {
        if (title.isNotEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.color_262626)
            )
        } else {
            titleComposable.invoke()
        }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically, content = actions
        )
    }
}