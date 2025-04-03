package com.zhq.jetpackcomposelearn.ui.screen.projects

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/3 14:04
 * Description
 */
sealed class DataResults<T> {
    data class Success<T>(val data: T) : DataResults<T>()
    data class Error<T>(val message: String, val data: T? = null) : DataResults<T>()
    class Loading<T> : DataResults<T>()
    class Refreshing<T> : DataResults<T>()
}