package com.zhq.jetpackcomposelearn.ui.screen.course

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.jetpackcomposelearn.common.BaseScreen
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 15:50
 * Description
 */
@Serializable
data class CourseCatalogRoute(val title: String, val id: Int)

@Composable
fun CourseCatalogScreen(
    route: CourseCatalogRoute,
    navHostController: NavHostController,
    viewModel: CourseCatalogViewModel = hiltViewModel(),
    onCourseCatalogItemClick: (ArticleDTO) -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()

    BaseScreen(title = route.title, onBack = { navHostController.popBackStack() }) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "目录",
                fontSize = 23.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
            )
            Divider(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .height(1.dp)
                    .fillMaxWidth(),
                color = Color.LightGray
            )

            BaseRefreshListContainer(
                uiPageState = uiPageState,
                contentPadding = PaddingValues(12.dp),
                itemSpace = 0.dp,
                onRefresh = { viewModel.getCourseCatalogList(route.id, true) },
                onLoadMore = {
                    viewModel.getCourseCatalogList(route.id, false)
                }) {
                CourseCatalogItemView(item = it) { item: ArticleDTO ->
                    onCourseCatalogItemClick.invoke(item)
                }
            }
        }
    }

}

@Composable
private fun CourseCatalogItemView(
    item: ArticleDTO,
    onCourseCatalogItemClick: (ArticleDTO) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                onCourseCatalogItemClick.invoke(item)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.title,
                color = Color.Black,
                fontWeight = FontWeight.W200,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight, contentDescription = ""
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Color.LightGray
        )
    }
}

