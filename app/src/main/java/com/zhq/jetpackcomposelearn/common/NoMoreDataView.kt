package com.zhq.jetpackcomposelearn.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhq.jetpackcomposelearn.R

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 17:14
 * Description
 */
@Composable
fun NoMoreDataView(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.noMoreData),
            color = LocalContentColor.current.copy(alpha = 0.6f),
            fontSize = 14.sp
        )
    }
}