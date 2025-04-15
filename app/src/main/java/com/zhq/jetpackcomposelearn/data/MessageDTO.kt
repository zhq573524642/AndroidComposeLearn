package com.zhq.jetpackcomposelearn.data

import com.zhq.commonlib.ext.ProvideItemKeys

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/14 11:20
 * Description
 */
data class MessageDTO(
    val id: Int,
    val fromUser: String,
    val title: String,
    val message: String,
    val tag: String,
    val niceDate: String,
    val fullLink: String,

):ProvideItemKeys{
    override fun provideKey(): Int {
        return id
    }
}
