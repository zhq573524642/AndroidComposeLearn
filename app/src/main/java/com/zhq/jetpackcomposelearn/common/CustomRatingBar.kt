package com.zhq.jetpackcomposelearn.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/17 13:37
 * Description
 */
@Composable
fun CustomRatingBar(
    title: String = "优先级 ",
    ratingCount: Int = 5,
    ratingSelected: Int = -1,
    ratingSize: Dp = 25.dp,
    selectedColor: Color = Color.Red,
    unSelectedColor: Color = Color.LightGray,
    onRatingBarSelectListener: (Int) -> Unit
) {
    var selectedIndex by remember {
        mutableStateOf(ratingSelected)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 13.sp, color = Color.Black)
        for (i in 0 until ratingCount) {
            Icon(
                tint = if (i <= selectedIndex) selectedColor else unSelectedColor,
                imageVector = Icons.Default.Star, contentDescription = "",
                modifier = Modifier
                    .size(ratingSize)
                    .clickable {
                        if (i == 0 && selectedIndex == 0) {
                            selectedIndex = -1
                            onRatingBarSelectListener.invoke(0)
                        } else {
                            selectedIndex = i
                            onRatingBarSelectListener.invoke(selectedIndex + 1)
                        }

                    }
            )
        }
    }
}