package com.zhq.jetpackcomposelearn.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhq.jetpackcomposelearn.R

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/7 13:59
 * Description
 */

@Composable
fun CommonInputView(
    label: String = "账号",
    enable: Boolean = true,
    labelView: @Composable (() -> Unit)? = null,
    onInputCallback: (String) -> Unit
) {
    var inputContent by remember {
        mutableStateOf("")
    }
    val brush = remember {
        Brush.linearGradient(
            colors = listOf(Color.Red, Color.Yellow, Color.Green,Color.Blue,Color.Magenta)
        )
    }
    OutlinedTextField(
        value = inputContent,
        onValueChange = {
            inputContent = it
            onInputCallback.invoke(it)
        },
        enabled = enable,
        label = {
            if (label.isNotEmpty()) {
                Text(
                    text = label,
                    fontSize = 13.sp,
                    color = colorResource(id = R.color.color_262626)
                )
            } else {
                labelView?.invoke()
            }

        },
        textStyle = TextStyle(brush = brush),
        shape = RoundedCornerShape(10.dp)
    )
}