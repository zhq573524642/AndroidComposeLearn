package com.zhq.jetpackcomposelearn.ui.screen.mine.collect

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseUiStatePage
import com.zhq.commonlib.utils.ColorUtils
import com.zhq.jetpackcomposelearn.data.OfficialAccountDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 11:21
 * Description
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OfficialAccountsView(
    viewModel: OfficialAccountViewModel = hiltViewModel(),
    onOfficialAccountClick: (OfficialAccountDTO) -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    BaseUiStatePage(uiPageState = uiPageState, onRefresh = {
        viewModel.getOfficialAccountList(true)
    }) { list: List<OfficialAccountDTO> ->
        FlowRow(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 4
        ) {
            list.forEach { item: OfficialAccountDTO ->
                Text(
                    text = item.name,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(3.dp),
                            color = Color(ColorUtils.generateRandomColorLong())
                        )
                        .padding(start = 5.dp, end = 5.dp)
                        .clickable {
                            onOfficialAccountClick.invoke(item)
                        }
                )
            }
        }
    }
}