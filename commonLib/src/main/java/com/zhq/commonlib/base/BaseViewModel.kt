package com.zhq.commonlib.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.commonlib.data.model.UiState
import com.zhq.commonlib.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 15:51
 * Description
 */
private const val TAG = "BaseViewModel"
abstract class BaseViewModel<T> : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<T>>(UiState(true))
    val uiState: StateFlow<UiState<T>> = _uiState

    protected  fun emitUiState(
        showLoading: Boolean = false,
        data: T? = null,
        error: String? = null,
        showLoadingMore: Boolean = false,
        noMoreData: Boolean = false
    ) {
        _uiState.value = UiState(showLoading, data, error, showLoadingMore, noMoreData)
    }


    fun launch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.() -> Unit = {},
        finallyBlock: suspend CoroutineScope.() -> Unit = {}
    ) {
        // 默认是执行在主线程，相当于launch(Dispatchers.Main)
        viewModelScope.launch {
            try {
                tryBlock()
            } catch (e: Exception) {
                Log.d(TAG, "===Launch异常: ${e.message}")
                BaseApp.baseAppViewModel.emitException(e)
                catchBlock()
            } finally {
                finallyBlock()
            }
        }
    }

    /**
     * 请求结果处理
     *
     * @param response ApiResponse
     * @param successBlock 服务器请求成功返回成功码的执行回调，默认空实现
     * @param errorBlock 服务器请求成功返回错误码的执行回调，默认返回false的空实现，函数返回值true:拦截统一错误处理，false:不拦截
     */
    suspend fun <T> handleRequest(
        response: BaseResponse<T>,
        errorBlock: suspend CoroutineScope.(response: BaseResponse<T>) -> Boolean = { false },
        successBlock: suspend CoroutineScope.(response: BaseResponse<T>) -> Unit = {}
    ) {
        coroutineScope {
            when (response.errorCode) {
                BaseConfigs.HTTP_SUCCESS -> successBlock(response) // 服务器返回请求成功码
                else -> { // 服务器返回的其他错误码
                    LogUtil.d("====登录${response.errorMsg}")
                    if (!errorBlock(response)) {
                        // 只有errorBlock返回false不拦截处理时，才去统一提醒错误提示
                        BaseApp.baseAppViewModel.emitErrorResponse(response)
                    }
                }
            }
        }
    }
}