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
import com.zhq.jetpackcomposelearn.common.HorizontalSpace
import com.zhq.jetpackcomposelearn.data.ArticleDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/11 13:53
 * Description
 */
private const val TAG = "CollectArticleView"

@Composable
fun CollectWebsiteView(
    viewModel: CollectWebsiteViewModelBase = hiltViewModel(),
    onWebsiteItemClick: (ArticleDTO) -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    val unCollectEvent by viewModel.unCollectEvent.collectAsState()
    var dialogOpen by remember {
        mutableStateOf(false)
    }
    var websiteId by remember {
        mutableStateOf(0)
    }
    // 我收藏的文章列表中取消收藏
    if (unCollectEvent != null) {
        uiPageState.data = uiPageState.data?.filter {
            it.id != unCollectEvent
        }
    }
    BaseUiStateListPage(
        uiPageState = uiPageState,
        contentPadding = PaddingValues(12.dp),
        itemSpace = 12.dp,
        onRefresh = { viewModel.getMyCollectWebsite(true) }) { item: ArticleDTO ->
        CollectWebsiteItem(
            modifier = Modifier
                .fillMaxWidth()
                .animateItem(),
            item = item,
            onUnCollectWebsite = {
                websiteId = it.id
                dialogOpen = true
            }
        ) {
            onWebsiteItemClick.invoke(it)
        }
        if (dialogOpen) {
            CommonRemindDialog(msg = "是否取消收藏？",
                onDialogDismiss = { dialogOpen = false },
                onCancelCallback = { dialogOpen = false }) {
                dialogOpen = false
                viewModel.deleteCollectWebsite(
                    id = websiteId
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CollectWebsiteItem(
    modifier: Modifier = Modifier,
    item: ArticleDTO,
    onUnCollectWebsite: (ArticleDTO) -> Unit,
    onWebsiteItemClick: (ArticleDTO) -> Unit
) {
    Card(
        onClick = {
            onWebsiteItemClick.invoke(item)
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
            ) {
                Text(
                    text = item.name,
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                HorizontalSpace(height = 10.dp)
                Text(
                    text = item.link,
                    fontSize = 13.sp,
                    color = Color.Blue,
                    maxLines = 2
                )
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
                            onUnCollectWebsite.invoke(item)
                        },
                    tint = Color.Red,
                    painter = painterResource(id = R.drawable.ic_remove_collect),
                    contentDescription = "取消收藏"
                )
            }
        }
    }
}
