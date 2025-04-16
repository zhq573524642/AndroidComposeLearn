package com.zhq.jetpackcomposelearn.ui.screen.systems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 13:49
 * Description
 */

@Serializable
data class SystemChildListRoute(
    val name: String, val id: Int
)

@Composable
fun SystemsChildListScreen(
    route: SystemChildListRoute,
    navHostController: NavHostController,
    viewModel: SystemChildViewModel = hiltViewModel(),
    onAuthorClick: (ArticleDTO) -> Unit,
    onItemClick: (ArticleDTO) -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseRefreshListContainer(
        topAppBar = {
            CenterTitleHeader(title = route.name, onBack = { navHostController.popBackStack() })
        },
        uiPageState = uiPageState,
        contentPadding = PaddingValues(12.dp),
        onRefresh = { viewModel.getSystemChildList(route.id, true) },
        onLoadMore = {
            viewModel.getSystemChildList(route.id, false)
        }) {
        ItemView(item = it,
            onAuthorClick = { item: ArticleDTO ->
                onAuthorClick.invoke(item)
            }) { item: ArticleDTO ->
            onItemClick.invoke(item)
        }
    }
}

@Composable
private fun ItemView(
    item: ArticleDTO,
    onAuthorClick: (ArticleDTO) -> Unit,
    onItemClick: (ArticleDTO) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke(item)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = item.niceDate, fontSize = 12.sp, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (item.author.isNotEmpty()) "作者：${item.author}" else "分享者：${item.shareUser}",
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        onAuthorClick.invoke(item)
                    }
                )

                Icon(
                    imageVector = if (item.collect) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (item.collect) "已收藏" else "未收藏"
                )
            }
        }
    }
}