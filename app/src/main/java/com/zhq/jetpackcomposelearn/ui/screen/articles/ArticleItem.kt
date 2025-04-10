package com.zhq.jetpackcomposelearn.ui.screen.articles

import android.text.TextUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.ui.screen.ArticleViewModel

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 16:50
 * Description
 */
@Composable
fun ArticleItem(
    item: ArticleDTO,
    articleViewModel: ArticleViewModel,
    articleItemClick: (ArticleDTO) -> Unit
) {
    Card(
        onClick = {
            articleItemClick.invoke(item)
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 10.dp,
            disabledElevation = 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardColors(
            containerColor = Color.White, contentColor = Color.LightGray,
            disabledContentColor = Color.LightGray, disabledContainerColor = Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f)
                        .padding(end = 10.dp),
                    text = "${item.superChapterName}/${item.chapterName}",
                    fontSize = 12.sp,
                    color = Color(0xff6b00ff),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.niceDate,
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
            }

            Text(
                text = item.title,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xff262626),
                fontWeight = FontWeight.Bold,
                maxLines = 3
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val author = if (!TextUtils.isEmpty(item.shareUser))
                    "分享者：${item.shareUser}" else "作者：${item.author}"
                Text(
                    text = author,
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    textDecoration = TextDecoration.Underline,

                    )
                androidx.compose.material3.Icon(
                    imageVector =
                    Icons.Filled.Favorite,
                    contentDescription = if (item.collect) "已收藏" else "未收藏",
                    tint = if (item.collect) Color.Red else Color.LightGray
                )
            }
        }
    }

}