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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.common.CommonRemindDialog
import com.zhq.jetpackcomposelearn.common.HorizontalSpace
import com.zhq.jetpackcomposelearn.common.ShareDialog
import com.zhq.jetpackcomposelearn.common.VerticalSpace
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 13:37
 * Description
 */
@Serializable
data class ArticleRoute(val from: FromEnum, val title: String, val id: Int = 0,val author:String="")

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
    val deleteShareArticleEvent by viewModel.deleteShareArticleEvent.collectAsState()
    //收藏文章更新页面
    if (collectEvent != null) {
        uiPageState.data?.forEach {
            if (it.id == collectEvent!!.id) {
                it.collect = collectEvent!!.collect
            }
        }
    }
    //删除我分享的文章
    if (deleteShareArticleEvent != null) {
        uiPageState.data = uiPageState.data?.filter {
            it.id != deleteShareArticleEvent
        }
    }
    //初始页面收藏文章状态
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

    var openShareDialog by remember {
        mutableStateOf(false)
    }

    var openRemindDialog by remember {
        mutableStateOf(false)
    }

    var openEditDialog by remember {
        mutableStateOf(false)
    }

    var articleId by remember {
        mutableStateOf(0)
    }
    var articleName by remember {
        mutableStateOf("")
    }
    var articleAuthor by remember {
        mutableStateOf("")
    }
    var articleLink by remember {
        mutableStateOf("")
    }

    fun isShowSearchIcon(): Boolean {
        return route.from == FromEnum.officialAccountSearch ||
                route.from == FromEnum.officialAccountHistory
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
            //我的分享列表
            FromEnum.myShareArticleList -> {
                viewModel.getArticleListForMyself(isRefresh)
            }
            //作者的文章列表
            FromEnum.authorArticleList->{
                viewModel.getArticleListForAuthor(isRefresh,route.author)
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
                    if (isShowSearchIcon()) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "搜索",
                            modifier = Modifier.clickable {

                            })
                    }

                })
        },
        isShowFloatButton = route.from == FromEnum.myShareArticleList,
        onFloatButtonClick = {
            openShareDialog = true
        },
        isHeaderScrollWithList = false,
        headerContent = {
            if (route.from == FromEnum.shareUserArticleList ||
                route.from == FromEnum.myShareArticleList
            ) {
                ShareUserInfo(viewModel = viewModel)
            }
        }) { articleItem: ArticleDTO ->
        ArticleItem(item = articleItem, baseArticleViewModel = viewModel,
            isShowEditButton = route.from == FromEnum.myShareArticleList,
            onEditClick = { i ->
                articleId = i.id
                articleName = i.title
                articleAuthor = i.author
                articleLink = i.link
                openEditDialog = true
            },
            onDeleteClick = { i ->
                articleId = i.id
                openRemindDialog = true
            },
            onAuthorClick = { article: ArticleDTO, isAuthor: Boolean ->
                if (route.from == FromEnum.shareUserArticleList) {
                    onAuthorClick.invoke(article, isAuthor)
                }
            }) { item: ArticleDTO ->
            onArticleItemClick.invoke(item)
        }

    }

    //分享文章编辑弹窗
    if (openShareDialog) {
        ShareDialog(
            title = "分享文章",
            onDismissRequest = {
                openShareDialog = false
            }) { title: String, author: String, link: String ->
            openShareDialog = false
            viewModel.postShareArticle(title, author, link) {
                viewModel.getArticleListForMyself(true)
            }
        }
    }

    //编辑分享的文章弹窗
    if (openEditDialog) {
        ShareDialog(
            title = "编辑分享",
            name = articleName,
            author = articleAuthor,
            link = articleLink,
            onDismissRequest = {
                openEditDialog = false
            }) { title: String, author: String, link: String ->
            openEditDialog = false
            viewModel.postShareArticle(title, author, link, false) {
                viewModel.getArticleListForMyself(true)
            }

        }
    }

    if (openRemindDialog) {
        CommonRemindDialog(msg = "是否删除分享的文章？",
            onDialogDismiss = { openRemindDialog = false },
            onCancelCallback = { openRemindDialog = false }) {
            openRemindDialog = false
            viewModel.deleteShareArticle(articleId)
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