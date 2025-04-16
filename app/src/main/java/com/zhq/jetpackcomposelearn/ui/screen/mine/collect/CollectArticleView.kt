package com.zhq.jetpackcomposelearn.ui.screen.mine.collect

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseUiStateListPage
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.common.CommonRemindDialog
import com.zhq.jetpackcomposelearn.common.ShareDialog
import com.zhq.jetpackcomposelearn.data.ArticleDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/11 13:53
 * Description
 */
private const val TAG = "CollectArticleView"

@Composable
fun CollectArticleView(
    viewModel: CollectArticleViewModel = hiltViewModel(),
    onArticleItemClick: (ArticleDTO) -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    val unCollectEvent by viewModel.unCollectEvent.collectAsState()
    val editEvent by viewModel.editEvent.collectAsState()
    var dialogOpen by remember {
        mutableStateOf(false)
    }
    var dialogCollect by remember {
        mutableStateOf(false)
    }
    var dialogEdit by remember {
        mutableStateOf(false)
    }
    var articleId by remember {
        mutableStateOf(0)
    }
    var articleTitle by remember {
        mutableStateOf("")
    }
    var articleAuthor by remember {
        mutableStateOf("")
    }
    var articleLink by remember {
        mutableStateOf("")
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
    //编辑文章 更新列表
    if (editEvent != null) {
        uiPageState.data?.forEach {
            if (it.id == editEvent!!.id) {
                it.title = editEvent!!.title
                it.author = editEvent!!.author
                it.link = editEvent!!.link
            }
        }
    }
    BaseUiStateListPage(
        uiPageState = uiPageState,
        contentPadding = PaddingValues(12.dp),
        itemSpace = 12.dp,
        onRefresh = { viewModel.getMyCollectArticle(true) },
        onLoadMore = {
            viewModel.getMyCollectArticle(false)
        },
        isShowFloatButton = true,
        onFloatButtonClick = {
            dialogCollect = true
        }) { item: ArticleDTO ->
        CollectArticleItem(
            modifier = Modifier
                .fillMaxWidth()
                .animateItem(), item = item,
            onEditCollectedArticle = {
                articleId = it.id
                articleTitle = it.title
                articleLink = it.author
                articleLink = it.link
                dialogEdit = true
            },
            onUnCollectArticle = {
                articleId = it.id
                originId = it.originId
                dialogOpen = true
            }) {
            onArticleItemClick.invoke(it)
        }

    }

    //删除收藏提示
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

    //收藏文章弹窗
    if (dialogCollect) {
        ShareDialog(title = "收藏文章", onDismissRequest = {
            dialogCollect = false
        }) { title: String, author: String, link: String ->
            dialogCollect = false
            viewModel.postCollectArticleForExternal(title, author, link) {
                viewModel.getMyCollectArticle(true)
            }
        }
    }

    if (dialogEdit) {
        ShareDialog(title = "编辑收藏",
            name = articleTitle,
            author = articleAuthor,
            link = articleLink,
            onDismissRequest = {
                dialogEdit = false
            }) { title: String, author: String, link: String ->
            dialogEdit = false
            viewModel.handleEditCollectedArticle(articleId, title, author, link)
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CollectArticleItem(
    modifier: Modifier = Modifier,
    item: ArticleDTO,
    onEditCollectedArticle: (ArticleDTO) -> Unit,
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
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { onEditCollectedArticle.invoke(item) },
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