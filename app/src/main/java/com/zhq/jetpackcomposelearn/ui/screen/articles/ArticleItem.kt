package com.zhq.jetpackcomposelearn.ui.screen.articles

import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.size.Dimension
import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import kotlin.math.roundToInt

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 16:50
 * Description
 */
private const val TAG = "ArticleItem"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleItem(
    item: ArticleDTO,
    baseArticleViewModel: BaseArticleViewModel,
    isShowEditButton: Boolean = false,
    onEditClick: ((ArticleDTO) -> Unit)? = null,
    onDeleteClick: ((ArticleDTO) -> Unit)? = null,
    onAuthorClick: ((ArticleDTO, Boolean) -> Unit)? = null,
    onArticleItemClick: (ArticleDTO) -> Unit
) {

    Card(
        onClick = {
            onArticleItemClick.invoke(item)
        },
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.fresh) {
                    Text(
                        text = "新", color = Color.Red, fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                        ),
                        modifier = Modifier
                            .border(1.dp, color = Color.Red, shape = RoundedCornerShape(3.dp))
                            .padding(top = 4.dp, bottom = 4.dp, start = 5.dp, end = 5.dp)
                    )
                }

                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = "${item.superChapterName}/${item.chapterName}",
                    fontSize = 12.sp,
                    color = Color(0xff6b00ff),
                    maxLines = 1,
                    textAlign = TextAlign.Start,
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
                    modifier = Modifier.clickable {
                        onAuthorClick?.invoke(item, TextUtils.isEmpty(item.shareUser))
                    }
                )
                androidx.compose.material3.Icon(
                    imageVector =
                    Icons.Filled.Favorite,
                    contentDescription = if (item.collect) "已收藏" else "未收藏",
                    tint = if (item.collect) Color.Red else Color.LightGray,
                    modifier = Modifier.clickable {
                        baseArticleViewModel.handleCollectArticle(!item.collect, item.id)
                    }
                )
            }


        }
        if (isShowEditButton) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Green)
                        .clickable {
                            onEditClick?.invoke(item)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        painter = painterResource(id = R.drawable.ic_edit_collect),
                        contentDescription = "编辑",
                        tint = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Red)
                        .clickable {
                            onDeleteClick?.invoke(item)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        painter = painterResource(id = R.drawable.ic_remove_collect),
                        contentDescription = "删除",
                        tint = Color.White
                    )
                }
            }
        }

    }

}