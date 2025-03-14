package com.zhq.commonlib.data.model

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 15:53
 * Description
 */
open class UiState<T>(
    val showLoading: Boolean = false,
    var data: T? = null,
    val error: String? = null,
    val showLoadingMore: Boolean = false,
    val noMoreData: Boolean = false
)