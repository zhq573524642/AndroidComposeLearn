package com.zhq.jetpackcomposelearn.ui.screen.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseUiStateListPage
import com.zhq.commonlib.utils.ColorUtils
import com.zhq.jetpackcomposelearn.common.DynamicStatusBarScreen
import com.zhq.jetpackcomposelearn.common.HorizontalSpace
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.SearchHotKeyDTO
import com.zhq.jetpackcomposelearn.ui.screen.articles.ArticleItem

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 17:11
 * Description
 */
//@Serializable
//object SearchScreen
private const val TAG = "SearchScreen"

@Composable
fun SearchScreen(
    viewModel: SearchViewModelBase = hiltViewModel(),
    onBackClick: () -> Unit,
    onSearchResultItemClick: (ArticleDTO) -> Unit,
    onCommonWebsiteItemClick: (SearchHotKeyDTO) -> Unit
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var showSearchLayout by remember {
        mutableStateOf(true)
    }

    val uiPageState by viewModel.uiPageState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        //在显示搜索布局的时候请求焦点 显示键盘
        if (showSearchLayout) {
            focusRequester.requestFocus()
        }
    })

    DynamicStatusBarScreen(
        statusBarColor = Color.White,
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            onBackClick.invoke()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "返回")
                }

                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    ),
                    value = searchText, onValueChange = { value ->
                        searchText = value
                        if (searchText.isEmpty()) {
                            showSearchLayout = true
                        }
                    },
                    placeholder = {
                        Text(text = "请输入搜索关键词", color = Color.LightGray)
                    },
                    trailingIcon = {
                        if (searchText.isNotEmpty()) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "清空",
                                modifier = Modifier.clickable {
                                    searchText = ""
                                    showSearchLayout = true
                                })
                        }
                    })
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            //显示搜索结果页面
                            showSearchLayout = false
                            //清除输入焦点，隐藏键盘
                            focusManager.clearFocus(true)
                            viewModel.searchKeyword(true, searchText)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = "搜索",
                        tint = if (searchText.isNotEmpty()) Color.Blue else Color.LightGray
                    )
                }

            }
            if (showSearchLayout) {
                //显示搜索页面
                HorizontalSpace(height = 20.dp)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    SearchHotWords(viewModel = viewModel) {
                        searchText = it.name
                    }
                    HorizontalSpace(height = 30.dp)
                    CommonWebsite(viewModel = viewModel) {
                        onCommonWebsiteItemClick.invoke(it)
                    }
                }
            } else {
                //显示搜索结果页面
                BaseUiStateListPage(
                    uiPageState = uiPageState,
                    contentPadding = PaddingValues(12.dp),
                    onRefresh = { viewModel.searchKeyword(true, searchText) },
                    onLoadMore = {
                        viewModel.searchKeyword(false, searchText)
                    }) {
                    ArticleItem(item = it, baseArticleViewModel = viewModel) {
                        onSearchResultItemClick.invoke(it)
                    }
                }
            }

        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchHotWords(
    viewModel: SearchViewModelBase,
    onSearchKeyItemClick: (SearchHotKeyDTO) -> Unit
) {
    val list = viewModel.hotKeyList.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Text(text = "搜索热词", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        HorizontalSpace(height = 15.dp)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            maxItemsInEachRow = 4
        ) {

            list.value.forEachIndexed { index: Int, item: SearchHotKeyDTO ->
                Text(
                    text = item.name,
                    color = Color(ColorUtils.generateRandomColorLong()),
                    modifier = Modifier
                        .border(
                            1.dp,
                            color = Color(ColorUtils.generateRandomColorLong()),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
                        .clickable {
                            onSearchKeyItemClick.invoke(item)
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CommonWebsite(
    viewModel: SearchViewModelBase,
    onCommonWebsiteItemClick: (SearchHotKeyDTO) -> Unit
) {
    val list = viewModel.websiteList.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        Text(text = "常用网站", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        HorizontalSpace(height = 15.dp)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            maxItemsInEachRow = 4
        ) {

            list.value.forEachIndexed { index: Int, item: SearchHotKeyDTO ->
                Text(
                    text = item.name,
                    color = Color(ColorUtils.generateRandomColorLong()),
                    modifier = Modifier
                        .border(
                            1.dp,
                            color = Color(ColorUtils.generateRandomColorLong()),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
                        .clickable {
                            onCommonWebsiteItemClick.invoke(item)
                        }
                )
            }
        }
    }
}