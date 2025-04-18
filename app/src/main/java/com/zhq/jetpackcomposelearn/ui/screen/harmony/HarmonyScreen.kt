package com.zhq.jetpackcomposelearn.ui.screen.harmony

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.common.DynamicStatusBarScreen
import com.zhq.jetpackcomposelearn.data.ArticleDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 14:24
 * Description
 */
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun HarmonyScreen(
    modifier: Modifier = Modifier,
    viewModel: HarmonyViewModel = hiltViewModel(),
    onSearchClick: (key: String) -> Unit,
    onItemClick: (String, String) -> Unit
) {
    val datas by viewModel.harmonyData.collectAsState()

    LaunchedEffect(true) {
        if (datas == null) {
            viewModel.getHarmonyData()
        }
    }
    DynamicStatusBarScreen(
        statusBarColor = Color(0xff262626),
        backgroundColor = Color(0xfff5f5f5)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            HarmonyHeader {
                onSearchClick.invoke(it)
            }
            Column(
                modifier = Modifier.background(Color(0xfff5f5f5))
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    stickyHeader {
                        HarmonyItemHeader(title = datas?.tools?.name ?: "")
                    }
                    itemsIndexed(
                        datas?.tools?.articleList ?: emptyList()
                    ) { index: Int, item: ArticleDTO ->
                        HarmonyItem(title = item.title, desc = item.desc, link = item.link) {
                            onItemClick.invoke(item.title, item.link)
                        }
                    }

                    stickyHeader {
                        HarmonyItemHeader(title = datas?.links?.name ?: "")
                    }
                    itemsIndexed(
                        datas?.links?.articleList ?: emptyList()
                    ) { index: Int, item: ArticleDTO ->
                        HarmonyItem(title = item.title, desc = item.desc, link = item.link) {

                        }
                    }

                    stickyHeader {
                        HarmonyItemHeader(title = datas?.open_sources?.name ?: "")
                    }
                    itemsIndexed(
                        datas?.open_sources?.articleList ?: emptyList()
                    ) { index: Int, item: ArticleDTO ->
                        HarmonyItem(title = item.title, desc = item.desc, link = item.link) {

                        }
                    }
                }

            }
        }
    }


}

@Composable
fun HarmonyHeader(
    onSearchClick: (key: String) -> Unit
) {
    var inputContent by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xff262626)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
            text = "OpenHarmony三方库",
            fontSize = 25.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        TextField(value = inputContent,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 20.dp)
                .height(45.dp),
            onValueChange = {
                inputContent = it
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            trailingIcon = {
                Text(text = "搜索", modifier = Modifier.clickable {
                    if (inputContent.isEmpty()) {
                        "请输入收索内容".showToast()
                        return@clickable
                    }
                    onSearchClick.invoke(inputContent)
                })
            }
        )


    }
}

@Composable
fun HarmonyItemHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xfff5f5f5))
            .padding(start = 15.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun HarmonyItem(
    title: String,
    desc: String,
    link: String,
    onItemClick: (String) -> Unit
) {
    Card(
        onClick = { onItemClick.invoke(link) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = desc,
                fontSize = 12.sp,
                color = Color.DarkGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}



