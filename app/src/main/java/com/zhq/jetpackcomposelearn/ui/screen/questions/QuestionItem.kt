package com.zhq.jetpackcomposelearn.ui.screen.questions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.TagDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/14 11:08
 * Description
 */
@Composable
fun QuestionItem(
    data: ArticleDTO
) {

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                //标签
                if (!data.tags.isEmpty()) {
                    data.tags.forEachIndexed { index, tagDTO: TagDTO ->
                        TabView(tagDTO = tagDTO)
                    }
                }

                //分类
                Text(text = "分类:", fontSize = 11.sp, color = Color.LightGray)
                Text(
                    text = "${data.superChapterName}/${data.chapterName}",
                    fontSize = 11.sp,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.weight(1f))
                //时间
                Text(text = data.niceDate, fontSize = 11.sp, color = Color.LightGray)
            }
            //标题
            Text(
                text = data.title, fontSize = 15.sp, color = Color.Black,
                maxLines = 2, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dian_zan),
                    contentDescription = "",
                    modifier = Modifier
                        .width(15.dp)
                        .height(15.dp)
                )
                Text(
                    text = "${data.zan}", fontSize = 11.sp, color = Color.Red,
                    modifier = Modifier.padding(start = 3.dp, end = 5.dp)
                )
                //作者/分享者
                Text(
                    text = if (data.shareUser.isNotEmpty()) "分享者:${data.shareUser}"
                    else "作者:${data.author}",
                    fontSize = 12.sp, color = Color.DarkGray
                )
                Spacer(modifier = Modifier.weight(1f))
                androidx.compose.material3.Icon(
                    imageVector =
                    Icons.Filled.Favorite,
                    contentDescription = if (data.collect) "已收藏" else "未收藏",
                    tint = if (data.collect) Color.Red else Color.LightGray
                )
            }
        }
    }
}

@Composable
fun TabView(
    tagDTO: TagDTO
) {
    Row(
        modifier = Modifier
            .padding(end = 6.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            border = BorderStroke(1.dp, Color.Green),
            shape = RoundedCornerShape(3.dp),
        ) {
            Text(
                text = tagDTO.name,
                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp),
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false,
                    ),
                ),
                fontSize = 9.sp,
                color = Color.Green
            )
        }
    }
}