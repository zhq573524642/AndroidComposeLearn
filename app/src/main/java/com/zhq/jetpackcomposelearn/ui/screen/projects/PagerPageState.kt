package com.zhq.jetpackcomposelearn.ui.screen.projects

import com.zhq.jetpackcomposelearn.data.PageDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/3 15:07
 * Description
 */
data class PagerPageState<T>(
    val pageData: PageDTO<T> = PageDTO(curPage = 1, emptyList(), pageCount = 1),
    val isLoadingMore: Boolean = false,
    val error: String? = null
)