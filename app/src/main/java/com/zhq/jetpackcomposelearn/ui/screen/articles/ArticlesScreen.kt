package com.zhq.jetpackcomposelearn.ui.screen.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.common.HorizontalSpace
import com.zhq.jetpackcomposelearn.common.VerticalSpace
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 13:37
 * Description
 */
@Serializable
data class ArticleRoute(val from: FromEnum, val title: String, val id: Int = 0)

@Composable
fun ArticlesScreen(
    viewModel: ArticleViewModel = hiltViewModel(),
    route: ArticleRoute,
    onScreenBack: () -> Unit,
    onAuthorClick: (ArticleDTO, Boolean) -> Unit,
    onArticleItemClick: (ArticleDTO) -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    val user by App.appViewModel.user.collectAsState()
    val collectEvent by App.appViewModel.collectEvent.observeAsState()
    if (collectEvent != null) {
        uiPageState.data?.forEach {
            if (it.id == collectEvent!!.id) {
                it.collect = collectEvent!!.collect
            }
        }
    }
    if (user == null) {
        uiPageState.data?.forEach {
            it.collect = false
        }
    } else {
        uiPageState.data?.forEach {
            user?.userInfo?.collectIds?.forEach { id ->
                if (id == it.id) {
                    it.collect = true
                }
            }
        }
    }



    fun request(isRefresh: Boolean, from: FromEnum, id: Int) {
        when (from) {
            //公众号历史文章列表
            FromEnum.officialAccountHistory -> {
                viewModel.getArticleHistoryForOfficialAccount(isRefresh, id)
            }

            FromEnum.officialAccountSearch -> {}
            //广场数据
            FromEnum.squareArticleList -> {
                viewModel.getSquareArticleList(isRefresh)
            }
            //分享人列表
            FromEnum.shareUserArticleList -> {
                viewModel.getArticleListForShareUser(isRefresh, route.id)
            }
        }
    }
    BaseRefreshListContainer(
        uiPageState = uiPageState, onRefresh = {
            request(true, route.from, route.id)
        },
        onLoadMore = {
            request(false, route.from, route.id)
        },
        contentPadding = PaddingValues(12.dp),
        topAppBar = {
            CenterTitleHeader(title = route.title, onBack = { onScreenBack.invoke() },
                rightActions = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "搜索",
                        modifier = Modifier.clickable {

                        })
                })
        },
        isHeaderScrollWithList = false,
        headerContent = {
            if (route.from == FromEnum.shareUserArticleList) {
                ShareUserInfo(viewModel = viewModel)
            }
        }) {
        ArticleItem(item = it, baseArticleViewModel = viewModel,
            onAuthorClick = { article: ArticleDTO, isAuthor: Boolean ->
                onAuthorClick.invoke(article, isAuthor)
            }) { item: ArticleDTO ->
            onArticleItemClick.invoke(item)
        }

    }


}

@Composable
private fun ShareUserInfo(viewModel: ArticleViewModel) {
    val coinInfo by viewModel.coinInfo.collectAsState()
    val articleCount by viewModel.articleCount.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                buildAnnotatedString {
                    append("本站积分：")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 20.sp
                        )
                    ) {
                        append(coinInfo.coinCount)
                    }
                },
                fontSize = 15.sp,
                color = Color.Black
            )
            VerticalSpace(width = 8.dp)
            Text(
                text = "lv:${coinInfo.level}",
                modifier = Modifier
                    .background(
                        Color.Green, shape = RoundedCornerShape(3.dp)
                    )
                    .padding(5.dp)
            )
            VerticalSpace(width = 8.dp)
            Text(
                text = "排名:${coinInfo.rank}",
                modifier = Modifier
                    .background(
                        Color.Magenta, shape = RoundedCornerShape(3.dp)
                    )
                    .padding(5.dp)
            )
        }
        HorizontalSpace(height = 10.dp)
        Text(text = "分享了${articleCount}篇文章", fontSize = 14.sp, color = Color.DarkGray)
    }
}