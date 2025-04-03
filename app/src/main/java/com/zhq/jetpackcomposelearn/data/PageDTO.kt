package com.zhq.jetpackcomposelearn.data


data class PageDTO<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int=0,
    val over: Boolean=false,
    val pageCount: Int=1,
    val size: Int=1,
    val total: Int=1
)