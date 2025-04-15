package com.zhq.commonlib.base

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.commonlib.data.model.UiPageState
import com.zhq.commonlib.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 15:51
 * Description
 */
private const val TAG = "BaseViewModel"

abstract class BaseViewModel<T> : ViewModel() {


    private val _uiPageState = MutableStateFlow<UiPageState<T>>(UiPageState())
    val uiPageState: StateFlow<UiPageState<T>> = _uiPageState

    protected fun showLoading(isClearContent: Boolean = true, data: T? = null) {
        emitUiPageState(
            showContent = !isClearContent,
            showLoadingPage = isClearContent,
            showRefreshing = isClearContent,
            data = data
        )
    }

    protected fun showContent(data: T? = null, isLoadOver: Boolean = false) {
        emitUiPageState(
            showContent = true,
            showRefreshing = false,
            data = data,
            showLoadingMore = !isLoadOver,
            showNoMoreData = isLoadOver
        )
    }

    protected fun showEmpty(msg: String = "暂无数据") {
        emitUiPageState(
            showEmptyPage = true,
            msg = msg
        )
    }

    protected fun showError(msg: String = "加载失败") {
        emitUiPageState(
            showErrorPage = true,
            errorMsg = msg
        )
    }

    protected fun showLoadMoreError(data: T? = null, msg: String = "加载失败") {
        emitUiPageState(
            showContent = true,
            data = data,
            showLoadingMore = false,
            showLoadMoreError = true,
            errorMsg = msg
        )
    }

    protected fun emitUiPageState(
        showContent: Boolean = false,
        showLoadingPage: Boolean = false,
        showEmptyPage: Boolean = false,
        showErrorPage: Boolean = false,
        showRefreshing: Boolean = false,
        showLoadingMore: Boolean = false,
        showLoadMoreError: Boolean = false,
        showNoMoreData: Boolean = false,
        data: T? = null,
        msg: String = "",
        errorMsg: String = "",

        ) {
        _uiPageState.value = UiPageState(
            showContent,
            showLoadingPage,
            showEmptyPage,
            showErrorPage,
            showRefreshing,
            showLoadingMore,
            showLoadMoreError,
            showNoMoreData,
            data,
            msg,
            errorMsg
        )
    }


    fun launch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Exception) -> Boolean = { false },
        finallyBlock: suspend CoroutineScope.() -> Unit = {}
    ) {
        // 默认是执行在主线程，相当于launch(Dispatchers.Main)
        viewModelScope.launch {
            try {
                tryBlock()
            } catch (e: Exception) {
                Log.d(TAG, "===Launch异常: ${e.message}")
                if (!catchBlock(e)) {
                    BaseApp.baseAppViewModel.emitException(e)
                }

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