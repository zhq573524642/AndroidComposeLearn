package com.zhq.jetpackcomposelearn.ui.screen.projects

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zhq.jetpackcomposelearn.data.ArticleDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/7 16:23
 * Description
 */

@Composable
fun ProjectItem(
    item: ArticleDTO,
    viewModel: ProjectsViewModel,
    onItemClick: (ArticleDTO) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 2.dp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onItemClick.invoke(item)
            }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = item.envelopePic, contentDescription = "图片",
                modifier = Modifier
                    .width(90.dp)
                    .aspectRatio(9f / 16f)
                    .clip(RoundedCornerShape(10.dp))
                    .border(0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = item.title, fontSize = 18.sp,
                        color = Color.Black, fontWeight = FontWeight.Bold,
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.desc, fontSize = 13.sp, color = Color.DarkGray,
                        maxLines = 4, overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = item.author, color = Color.Black, fontSize = 13.sp)
                        Text(text = item.niceDate, color = Color.LightGray, fontSize = 12.sp)
                    }
                    Icon(
                        tint = if (item.collect) Color.Red else Color.LightGray,
                        imageVector = if (item.collect) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (item.collect) "已收藏" else "未收藏",
                        modifier = Modifier.clickable {
                            viewModel.handleCollectArticle(!item.collect,item.id)
                        }
                    )
                }
            }

        }
    }
}