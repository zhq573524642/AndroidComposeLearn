package com.zhq.jetpackcomposelearn.data


data class PageDTO<T>(
    val curPage: Int=1,
    val datas: List<T>,
    val offset: Int=0,
    var over: Boolean=false,
    val pageCount: Int=20,
    val size: Int=1,
    val total: Int=1
)