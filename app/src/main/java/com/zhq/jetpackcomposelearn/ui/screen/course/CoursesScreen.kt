package com.zhq.jetpackcomposelearn.ui.screen.course

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 15:06
 * Description
 */
@Serializable
data object CoursesRoute

@Composable
fun CoursesScreen(
    route: CoursesRoute,
    navHostController: NavHostController,
    viewModel: CourseViewModelBase = hiltViewModel(),
    onCourseItemClick: (ArticleDTO) -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseRefreshListContainer(
        topAppBar = {
            CenterTitleHeader(title = "教程", onBack = { navHostController.popBackStack() })
        },
        contentPadding = PaddingValues(12.dp),
        uiPageState = uiPageState, onRefresh = { viewModel.getCourseList(true)  }) {
        CourseItemView(item = it) { item: ArticleDTO ->
            onCourseItemClick.invoke(item)
        }
    }

}

@Composable
private fun CourseItemView(
    item: ArticleDTO,
    onItemClick: (ArticleDTO) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke(item)
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
                model = item.cover, contentDescription = "图片",
                modifier = Modifier
                    .width(90.dp)
                    .height(160.dp)
                    .aspectRatio(9f / 16f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = item.name,
                    color = Color.Blue,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "作者：${item.author}", fontSize = 13.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = item.desc, color = Color.LightGray, fontSize = 12.sp)
            }
        }
    }
}