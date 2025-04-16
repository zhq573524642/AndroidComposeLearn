package com.zhq.jetpackcomposelearn.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 16:21
 * Description
 */
@Composable
fun ShareDialog(
    title: String,
    name: String = "",
    author: String = "",
    link: String = "",
    onDismissRequest: () -> Unit,
    confirmButtonClick: (String, String, String) -> Unit
) {
    var inputTitle by remember {
        mutableStateOf(name)
    }
    var inputAuthor by remember {
        mutableStateOf(author)
    }
    var inputLink by remember {
        mutableStateOf(link)
    }
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title, color = Color.Black,
                    fontWeight = FontWeight.Bold, fontSize = 16.sp
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    label = { Text(text = "标题", fontSize = 13.sp, color = Color.Black) },
                    value = inputTitle, onValueChange = { value ->
                        inputTitle = value
                    })
                HorizontalSpace(height = 10.dp)
                OutlinedTextField(
                    label = { Text(text = "作者", fontSize = 13.sp, color = Color.Black) },
                    value = inputAuthor, onValueChange = { value ->
                        inputAuthor = value
                    })
                HorizontalSpace(height = 10.dp)
                OutlinedTextField(
                    label = { Text(text = "链接", fontSize = 13.sp, color = Color.Black) },
                    value = inputLink, onValueChange = { value ->
                        inputLink = value
                    })
            }
        },
        onDismissRequest = {
            onDismissRequest.invoke()
        },
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth(), onClick = {
                    confirmButtonClick.invoke(inputTitle, inputAuthor, inputLink)
                }) {
                Text(text = "确定")
            }
        })
}