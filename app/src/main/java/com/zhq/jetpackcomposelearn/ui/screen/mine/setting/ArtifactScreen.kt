package com.zhq.jetpackcomposelearn.ui.screen.mine.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseRefreshContainer
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.common.HorizontalSpace
import com.zhq.jetpackcomposelearn.data.ArtifactDTO
import com.zhq.jetpackcomposelearn.data.GoogleMavenDTO
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 17:21
 * Description
 */
@Serializable
data class ArtifactRoute(val key: String)

@Composable
fun ArtifactScreen(
    route: ArtifactRoute,
    viewModel: GoogleMavenPackageViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseRefreshContainer(uiPageState = uiPageState,
        topAppBar = {
            CenterTitleHeader(title = route.key, onBack = { onBackClick.invoke() })
        },
        onRefresh = {
            viewModel.queryPackageName(route.key)
        }) { itemList: List<GoogleMavenDTO> ->
        ItemView(item = itemList[0])
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemView(item: GoogleMavenDTO) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        state = rememberLazyListState()
    ) {
        item.artifactMap.forEach { (key: String, list: List<ArtifactDTO>) ->
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "库名：${key}", fontSize = 15.sp, color = Color.Blue)
                }
            }
            items(list) { item: ArtifactDTO ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 12.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,

                    ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "版本：${item.version}", fontSize = 13.sp)
                        HorizontalSpace(height = 12.dp)
                        Text(
                            text = "依赖：${item.content}",
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

}