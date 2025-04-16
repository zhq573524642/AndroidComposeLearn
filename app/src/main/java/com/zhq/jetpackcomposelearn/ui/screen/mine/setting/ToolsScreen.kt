package com.zhq.jetpackcomposelearn.ui.screen.mine.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.zhq.commonlib.base.widgets.BaseRefreshContainer
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.commonlib.base.widgets.BaseUiStateListPage
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.common.HorizontalSpace
import com.zhq.jetpackcomposelearn.common.VerticalSpace
import com.zhq.jetpackcomposelearn.data.ToolsDTO
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 16:02
 * Description
 */
@Serializable
data object ToolsRoute

//	https://www.wanandroid.com/resources/image/pc/tools/bejson.png
@Composable
fun ToolsScreen(
    viewModel: ToolsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onToolItemClick: (ToolsDTO) -> Unit
) {

    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseRefreshListContainer(uiPageState = uiPageState,
        topAppBar = {
            CenterTitleHeader(title = "工具", onBack = { onBackClick.invoke() })
        },
        onRefresh = { viewModel.getToolsList(true) },
        contentPadding = PaddingValues(12.dp)
    ) { item: ToolsDTO ->
        ToolsItem(item = item) {
            onToolItemClick.invoke(item)
        }
    }
}

@Composable
private fun ToolsItem(
    item: ToolsDTO,
    onToolItemClick: (ToolsDTO) -> Unit
) {
    val imgPath = "https://www.wanandroid.com/resources/image/pc/tools/"
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onToolItemClick.invoke(item)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = "${imgPath}${item.icon}",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop,
                contentDescription = "Icon"
            )
            VerticalSpace(width = 10.dp)
            Column {
                Text(
                    text = item.name,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                HorizontalSpace(height = 10.dp)
                Text(
                    text = item.desc, color = Color.DarkGray,
                    fontSize = 13.sp
                )
            }
        }

    }
}