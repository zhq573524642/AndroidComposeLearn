package com.zhq.jetpackcomposelearn.ui.screen.mine

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.common.DynamicStatusBarScreen
import com.zhq.jetpackcomposelearn.common.HorizontalSpace
import com.zhq.jetpackcomposelearn.common.VerticalSpace
import com.zhq.jetpackcomposelearn.data.UserInfoDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 14:26
 * Description
 */
@Composable
fun MineScreen(
    viewModel: MineViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    onTodoClick: () -> Unit,
    onIntegralClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    val userInfo = App.appViewModel.user.collectAsState()
    DynamicStatusBarScreen(isFullScreen = true) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_mine_bg), contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .blur(2.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .statusBarsPadding(),
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .size(25.dp)
                            .clickable { onTodoClick.invoke() },
                        painter = painterResource(id = R.drawable.ic_todo),
                        contentDescription = "待办"
                    )
                    Image(
                        modifier = Modifier
                            .size(25.dp)
                            .clickable { onIntegralClick.invoke() },
                        painter = painterResource(id = R.drawable.ic_integral),
                        contentDescription = "积分"
                    )
                    Image(
                        modifier = Modifier
                            .size(25.dp)
                            .clickable { onSettingClick.invoke() },
                        painter = painterResource(id = R.drawable.ic_setting),
                        contentDescription = "设置"
                    )
                }
                HorizontalSpace(height = 20.dp)
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, color = Color.White)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.ic_default_avatar),
                        contentDescription = "头像",
                    )
                }
                HorizontalSpace(height = 12.dp)
                Text(
                    text = viewModel.getUserName(),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        if (!UserManager.isLogin()) {
                            onLoginClick.invoke()
                        }
                    }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = viewModel.getUserLevel(), color = Color.Green, fontSize = 13.sp)
                    VerticalSpace(width = 20.dp)
                    Icon(
                        tint = Color.Yellow,
                        painter = painterResource(id = R.drawable.ic_integral_coin),
                        modifier = Modifier.size(20.dp), contentDescription = ""
                    )
                    VerticalSpace(width = 5.dp)
                    Text(
                        text = viewModel.getUserCoinCount(),
                        fontSize = 13.sp,
                        color = Color.Yellow
                    )
                }

            }


            Surface(
                modifier = Modifier
                    .padding(top = 230.dp)
                    .fillMaxSize(),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {

            }
        }
    }

}