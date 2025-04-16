package com.zhq.jetpackcomposelearn.ui.screen.mine.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
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
    onToolsClick: () -> Unit,
    onGoogleMavenClick:()->Unit,
    onLogoutClick: () -> Unit
) {

    BaseScreen(title = "设置", onBack = { navHostController.popBackStack() }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp,
                    end = 20.dp
                ).fillMaxWidth(), onClick = {
                    onToolsClick.invoke()
                }) {
                Text(text = "工具")
            }
            OutlinedButton(
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp,
                    end = 20.dp
                ).fillMaxWidth(), onClick = {
                    onGoogleMavenClick.invoke()
                }) {
                Text(text = "Google Maven 仓库快速查询")
            }
            Spacer(
                modifier = Modifier
                    .width(10.dp)
                    .weight(1f)
            )

            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(), onClick = {
                    onLogoutClick.invoke()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Purple40)
            ) {
                Text(text = "退出登录")
            }
        }
    }
}