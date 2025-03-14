package com.zhq.commonlib.base

import androidx.lifecycle.viewModelScope
import com.zhq.commonlib.data.model.BaseResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 16:03
 * Description
 */
open class BaseAppViewModel :BaseViewModel<Unit>() {

    /** 请求异常（服务器请求失败，譬如：服务器连接超时等） */
    private val _exception = MutableSharedFlow<Exception>()
    val exception: SharedFlow<Exception> = _exception

    /** 请求服务器返回错误（服务器请求成功但status错误，譬如：登录过期等） */
    private val _errorResponse = MutableSharedFlow<BaseResponse<*>>()
    val errorResponse: SharedFlow<BaseResponse<*>?> = _errorResponse

    /** emit请求出错 */
    fun emitException(exception: Exception) {
        viewModelScope.launch {
            _exception.emit(exception)
        }
    }

    /** emit请求错误信息 */
    fun emitErrorResponse(baseResponse: BaseResponse<*>) {
        viewModelScope.launch {
            _errorResponse.emit(baseResponse)
        }
    }
}