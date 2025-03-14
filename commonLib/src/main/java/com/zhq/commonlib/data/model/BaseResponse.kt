package com.zhq.commonlib.data.model

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 16:01
 * Description
 */
data class BaseResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)
