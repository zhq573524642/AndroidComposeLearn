package com.zhq.jetpackcomposelearn.ui.screen.mine.collect

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhq.commonlib.base.widgets.BaseUiStatePage
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.common.CommonRemindDialog
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.ui.screen.mine.MineViewModel

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/11 13:53
 * Description
 */
private const val TAG = "CollectArticleView"

@Composable
fun CollectArticleView(
    viewModel: MineViewModel,
    onArticleItemClick: (ArticleDTO) -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    val unCollectEvent by viewModel.unCollectEvent.collectAsState()
    var dialogOpen by remember {
        mutableStateOf(false)
    }
    var articleId by remember {
        mutableStateOf(0)
    }
    var originId by remember {
        mutableStateOf(0)
    }
    // 我收藏的文章列表中取消收藏
    if (unCollectEvent != null) {
        uiPageState.data = uiPageState.data?.filter {
            it.id != unCollectEvent
        }
    }
    BaseUiStatePage(
        uiPageState = uiPageState,
        contentPadding = PaddingValues(12.dp),
        itemSpace = 12.dp,
        onRefresh = { viewModel.getMyCollectArticle(true) },
        onLoadMore = {
            viewModel.getMyCollectArticle(false)
        }) { item: ArticleDTO ->
        CollectArticleItem(
            modifier = Modifier
                .fillMaxWidth()
                .animateItem(), item = item,
            onUnCollectArticle = {
                articleId = it.id
                originId = it.originId
                dialogOpen = true
            }) {
            onArticleItemClick.invoke(it)
        }
        if (dialogOpen) {
            CommonRemindDialog(msg = "是否取消收藏？",
                onDialogDismiss = { dialogOpen = false },
                onCancelCallback = { dialogOpen = false }) {
                dialogOpen = false
                viewModel.handleCollectArticleForMine(
                    isCollect = false,
                    id = articleId,
                    originId = originId
                ) {
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectArticleItem(
    modifier: Modifier = Modifier,
    item: ArticleDTO,
    onUnCollectArticle: (ArticleDTO) -> Unit,
    onArticleItemClick: (ArticleDTO) -> Unit
) {
    Card(
        onClick = {
            onArticleItemClick.invoke(item)
        },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(10.dp),
        elevation = 1.dp,
        modifier = modifier
            .fillMaxWidth()


    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "分类：${if (item.chapterName.isNotEmpty()) item.chapterName else "--"}",
                    color = Color.DarkGray, fontSize = 12.sp
                )
                Text(
                    text = item.title,
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "作者：${item.author}", color = Color.DarkGray, fontSize = 12.sp)
                    Text(
                        text = "收藏时间：${item.niceDate}",
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    tint = Color.Green,
                    painter = painterResource(id = R.drawable.ic_edit_collect),
                    contentDescription = "编辑收藏"
                )
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            //取消收藏
                            onUnCollectArticle.invoke(item)
                        },
                    tint = Color.Red,
                    painter = painterResource(id = R.drawable.ic_remove_collect),
                    contentDescription = "取消收藏"
                )
            }
        }
    }
}