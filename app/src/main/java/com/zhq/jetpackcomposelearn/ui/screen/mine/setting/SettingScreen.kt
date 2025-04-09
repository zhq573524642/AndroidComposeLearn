package com.zhq.jetpackcomposelearn.ui.screen.mine.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.common.BaseScreen
import com.zhq.jetpackcomposelearn.ui.theme.Purple40
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 14:53
 * Description
 */
@Serializable
data object SettingRoute

@Composable
fun SettingScreen(
    navHostController: NavHostController,
    onLogoutClick:()->Unit
) {

    BaseScreen(title = "设置", onBack = { navHostController.popBackStack() }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .width(10.dp)
                    .weight(1f)
            )

            Button(onClick = {
                onLogoutClick.invoke()
            },
                colors = ButtonDefaults.buttonColors(containerColor = Purple40)
            ) {
                Text(text = "退出登录")
            }
        }
    }
}