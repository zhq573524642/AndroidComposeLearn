package com.zhq.jetpackcomposelearn.common

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/17 14:30
 * Description
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    onDismissRequest: () -> Unit = {},
    onDismissButton: () -> Unit = {},
    onConfirmButton: (Long) -> Unit

) {
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,//初始化展示样式：选择、输入
    )
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,//是否立即全屏打开
        confirmValueChange = { value: SheetValue ->
            return@rememberModalBottomSheetState true
        }
    )
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        containerColor = Color.White,
        onDismissRequest = { onDismissRequest.invoke() },
        sheetState = sheetState
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            HorizontalSpace(height = 10.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Button(
                    onClick = { onDismissButton.invoke() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text(text = "取消")
                }
                VerticalSpace(width = 10.dp)
                Button(
                    onClick = {
                        onConfirmButton.invoke(datePickerState.selectedDateMillis ?: 0)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "确定")
                }
            }
            HorizontalSpace(height = 10.dp)
        }
    }


}