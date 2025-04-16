package com.zhq.jetpackcomposelearn.ui.screen.mine.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseRefreshListContainer
import com.zhq.jetpackcomposelearn.common.CenterTitleHeader
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 16:40
 * Description
 */
@Serializable
data object GoogleMavenPackageRoute

@Composable
fun GoogleMavenScreen(
    viewModel: GoogleMavenPackageViewModel = hiltViewModel(),
    onBackClick:()->Unit,
    onPackageNameItemClick: (String) -> Unit
) {
    val list by viewModel.packageNameList.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        CenterTitleHeader(title = "Google Maven包名", onBack = {onBackClick.invoke()})
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(list) { item: String ->
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(), onClick = { onPackageNameItemClick.invoke(item) }) {
                    Text(text = item, color = Color.Black, fontSize = 14.sp)
                }
            }
        }
    }
}