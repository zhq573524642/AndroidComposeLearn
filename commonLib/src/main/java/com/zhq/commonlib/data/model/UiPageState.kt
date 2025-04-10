package com.zhq.commonlib.data.model

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/10 11:18
 * Description
 */
open class UiPageState<T>(
    val showContent: Boolean = false,//页面内容显示
    val showLoadingPage: Boolean = false,//页面加载
    val showEmptyPage: Boolean = false,//页面空
    val showErrorPage: Boolean = false,//页面错误
    val showRefreshing: Boolean = false,//下拉刷新动画
    val showLoadingMore: Boolean = false,//上拉加载动画
    val showLoadMoreError: Boolean = false,//上拉加载出错
    val showNoMoreData: Boolean = false,//无更多数据
    var data: T? = null,//数据
    val msg: String = "暂无数据",//信息
    val errorMsg: String = "",//异常信息
)