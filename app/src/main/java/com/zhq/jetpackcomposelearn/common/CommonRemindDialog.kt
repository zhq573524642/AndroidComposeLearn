package com.zhq.jetpackcomposelearn.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/11 15:14
 * Description
 */

@Composable
fun CommonRemindDialog(
    msg: String,
    leftBtn: String = "取消",
    rightBtn: String = "确定",
    leftBtnBg: Color = Color.LightGray,
    rightBtnBg: Color = Color.Yellow,
    leftBtnTextColor: Color = Color.Black,
    rightBtnTextColor: Color = Color.Black,
    onDialogDismiss: () -> Unit = {},
    onCancelCallback: () -> Unit = {},
    onSureCallback: () -> Unit
) {

    androidx.compose.material.AlertDialog(
        title = {
            Text(
                text = "温馨提示", fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center){
                Text(
                    text = msg,
                    fontSize = 14.sp
                )
            }

        },
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = {
            onDialogDismiss.invoke()
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = leftBtnBg
                    ),
                    modifier = Modifier
                        .weight(1.0f),
                    onClick = {
                        onCancelCallback.invoke()
                    }) {
                    Text(text = leftBtn, color = leftBtnTextColor)
                }

                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = rightBtnBg
                    ),
                    modifier = Modifier
                        .weight(1.0f),
                    onClick = {
                        onSureCallback.invoke()
                    }) {
                    Text(text = rightBtn, color = rightBtnTextColor)
                }
            }

        })
}