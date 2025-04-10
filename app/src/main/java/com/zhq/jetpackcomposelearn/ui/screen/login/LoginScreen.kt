package com.zhq.jetpackcomposelearn.ui.screen.login

import android.text.TextUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.common.CommonInputView
import com.zhq.jetpackcomposelearn.common.DynamicStatusBarScreen

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/7 10:58
 * Description
 */
enum class LayoutType {
    login,
    account,
    register
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onClosePage: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var layoutType by rememberSaveable {
        mutableStateOf(LayoutType.login)
    }
    LaunchedEffect(key1 = Unit, block = {
        layoutType =
            if (UserManager.getLastUserName().isNotEmpty()) LayoutType.account else LayoutType.login
    })

    //调用登录接口
    fun callLogin(account: String, pwd: String, isShowLoginSuccessToast: Boolean = true) {
        viewModel.login(account, pwd,
            failedCall = { code, msg ->
                msg.showToast()
            }) {
            if (isShowLoginSuccessToast) {
                "登录成功".showToast()
            }
            onLoginSuccess.invoke()
        }
    }

    //调用注册接口
    fun callRegister(
        account: String,
        password: String,
        repassword: String
    ) {
        viewModel.register(account, password, repassword,
            failedCall = { code, msg ->
                msg.showToast()

            }) {
            "注册成功".showToast()
            callLogin(account, password, false)
        }
    }

   DynamicStatusBarScreen(
       statusBarColor = colorResource(id = R.color.color_c9eaf9),
       backgroundColor = colorResource(id = R.color.color_c9eaf9)
   ) {
       Box(
           modifier = Modifier
               .background(colorResource(id = R.color.color_c9eaf9))
               .fillMaxSize()
       ) {

           Box(
               modifier = Modifier
                   .height(50.dp)
                   .width(50.dp)
                   .clickable {
                       when (layoutType) {
                           LayoutType.login,
                           LayoutType.account -> {
                               onClosePage.invoke()
                           }

                           LayoutType.register -> {
                               if (UserManager
                                       .getLastUserName()
                                       .isNotEmpty()
                               ) {
                                   layoutType = LayoutType.account
                               } else {
                                   layoutType = LayoutType.login
                               }
                           }

                       }
                   },
               contentAlignment = Alignment.Center
           ) {
               Image(
                   modifier = Modifier
                       .width(23.dp)
                       .height(23.dp),
                   contentScale = ContentScale.Fit,
                   painter = painterResource(id = if (layoutType == LayoutType.login) R.drawable.ic_close_page else R.drawable.ic_back_black),
                   contentDescription = if (layoutType == LayoutType.login) "关闭" else "返回"
               )
           }

           when (layoutType) {
               LayoutType.login -> {
                   LoginView(
                       onRegisterClick = {
                           //显示注册页面
                           layoutType = LayoutType.register
                       },
                   ) { account, pwd ->
                       //调用登录接口
                       callLogin(account, pwd)
                   }
               }

               LayoutType.account -> {
                   AccountView(
                       onLastUserLogin = {
                           //调用登录接口
                           callLogin(
                               UserManager.getLastUserName(),
                               UserManager.getLastUserPassword() ?: ""
                           )
                       }, onNewLogin = {
                           //显示登录页面
                           layoutType = LayoutType.login
                       }) {
                       //显示注册页面
                       layoutType = LayoutType.register
                   }
               }

               LayoutType.register -> {
                   RegisterView { account, pwd ->
                       //调用注册接口
                       callRegister(account, pwd, pwd)
                   }
               }

               else -> {

               }
           }

       }
   }
}

@Composable
fun LoginView(
    onRegisterClick: () -> Unit,
    onHandleLogin: (String, String) -> Unit
) {
    var inputAccount by remember {
        mutableStateOf("")
    }
    var inputPwd by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                shape = CircleShape,
                color = colorResource(id = R.color.color_c9eaf9),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_login_default_avatar),
                    contentDescription = "头像"
                )
            }
            Text(
                text = "欢迎登录", color = colorResource(id = R.color.purple_500),
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            CommonInputView(
                label = "账号",
            ) { value ->
                inputAccount = value

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            CommonInputView(
                label = "密码",
            ) { value ->
                inputPwd = value

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = colorResource(id = R.color.purple_500),
                    contentColor = Color.White,
                    disabledContainerColor = colorResource(id = R.color.purple_200),
                    disabledContentColor = Color.LightGray,
                ),
                onClick = {
                    onHandleLogin(inputAccount, inputPwd)
                }) {
                Text(text = "立即登录", color = Color.White)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
            Text(
                modifier = Modifier.clickable {
                    onRegisterClick.invoke()
                },
                text = "注册", color = colorResource(id = R.color.purple_500),
                fontSize = 14.sp
            )

        }
    }
}

@Composable
fun RegisterView(
    onHandleRegister: (String, String) -> Unit
) {
    var inputAccount by remember {
        mutableStateOf("")
    }
    var inputPwd1 by remember {
        mutableStateOf("")
    }
    var inputPwd2 by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                shape = CircleShape,
                color = colorResource(id = R.color.color_c9eaf9),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_login_default_avatar),
                    contentDescription = "头像"
                )
            }
            Text(
                text = "欢迎注册", color = colorResource(id = R.color.purple_500),
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            CommonInputView(
                label = "账号",
            ) { value ->
                inputAccount = value

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            CommonInputView(
                label = "密码",
            ) { value ->
                inputPwd1 = value

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            CommonInputView(
                label = "确认密码",
            ) { value ->
                inputPwd2 = value

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = colorResource(id = R.color.purple_500),
                    contentColor = Color.White,
                    disabledContainerColor = colorResource(id = R.color.purple_200),
                    disabledContentColor = Color.LightGray,
                ),
                onClick = {
                    if (inputAccount.isEmpty()) {
                        "请输入账号".showToast()
                        return@Button
                    }
                    if (inputPwd1.isEmpty()) {
                        "请输入密码".showToast()
                        return@Button
                    }
                    if (inputPwd1.length < 6) {
                        "密码不得少于6个字符".showToast()
                        return@Button
                    }
                    if (inputPwd2.isEmpty()) {
                        "请再次输入密码".showToast()
                        return@Button
                    }
                    if (!TextUtils.equals(inputPwd1, inputPwd2)) {
                        "两次密码输入不一致".showToast()
                        return@Button
                    }

                    onHandleRegister.invoke(inputAccount, inputPwd1)
                }) {
                Text(text = "立即注册", color = Color.White)
            }

        }
    }
}

@Composable
fun AccountView(
    onLastUserLogin: () -> Unit,
    onNewLogin: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                shape = CircleShape,
                color = colorResource(id = R.color.color_c9eaf9),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_login_default_avatar),
                    contentDescription = "头像"
                )
            }
            Text(
                text = UserManager.getLastUserName(),
                color = colorResource(id = R.color.purple_500),
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = colorResource(id = R.color.purple_500),
                    contentColor = Color.White,
                    disabledContainerColor = colorResource(id = R.color.purple_200),
                    disabledContentColor = Color.LightGray,
                ),
                onClick = {
                    onLastUserLogin.invoke()
                }) {
                Text(text = "以此账号登录", color = Color.White)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = colorResource(id = R.color.purple_500),
                    contentColor = Color.White,
                    disabledContainerColor = colorResource(id = R.color.purple_200),
                    disabledContentColor = Color.LightGray,
                ),
                onClick = {
                    onNewLogin.invoke()
                }) {
                Text(text = "不是你？换个账号", color = Color.White)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
            Text(
                modifier = Modifier.clickable {
                    onRegisterClick.invoke()
                },
                text = "注册", color = colorResource(id = R.color.purple_500),
                fontSize = 14.sp
            )
        }
    }
}