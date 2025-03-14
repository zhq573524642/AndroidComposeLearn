package com.zhq.jetpackcomposelearn.ui.screen.login

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.repo.LoginRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/7 15:19
 * Description
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: LoginRepositoryImpl) :
    BaseViewModel<String>() {

    fun login(
        account: String,
        password: String,
        failedCall: () -> Unit = {},
        successCall: () -> Unit = {}
    ) {
        launch({
            handleRequest(repo.login(account, password),
                errorBlock = {
                    failedCall.invoke()
                    false
                }) {
                UserManager.saveLastUserName(it.data.username)
                UserManager.storeLastUserPwd(password)
                UserManager.saveUser(it.data)
                App.appViewModel.emitUser(it.data)
                successCall.invoke()
            }
        })
    }

    fun register(
        account: String,
        password: String,
        repassword:String,
        failedCall: () -> Unit = {},
        successCall: () -> Unit = {}
    ){
        launch({
            handleRequest(repo.register(account,password,repassword),
                errorBlock = {
                    failedCall.invoke()
                    false
                }){
                successCall.invoke()
            }
        })
    }

}