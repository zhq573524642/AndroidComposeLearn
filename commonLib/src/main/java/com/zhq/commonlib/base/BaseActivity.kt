package com.zhq.commonlib.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.zhq.commonlib.utils.ToastUtils.showToast
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 15:49
 * Description
 */
open class BaseActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    protected open fun initObserver() {
        BaseApp.baseAppViewModel.apply {
            viewModelScope.launch {
                exception.flowWithLifecycle(lifecycle).collect { e ->
                    when (e) {
                        is SocketTimeoutException -> "网络请求超时，请稍后重试".showToast()

                        is ConnectException, is UnknownHostException -> "网络连接失败，请稍后重试".showToast()

                        else -> (e.message ?: "网络请求错误，请稍后重试").showToast()
                    }
                }
            }

            viewModelScope.launch {
                errorResponse.flowWithLifecycle(lifecycle).collect { response ->
                    response?.let { it1 -> it1.errorMsg.showToast() }
                }
            }
        }
    }
}