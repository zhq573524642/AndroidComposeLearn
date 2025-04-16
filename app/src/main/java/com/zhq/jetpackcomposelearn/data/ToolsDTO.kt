package com.zhq.jetpackcomposelearn.data

import com.zhq.commonlib.ext.ProvideItemKeys

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 16:05
 * Description
 */
data class ToolsDTO(
    val id:Int,
    val icon:String,
    val name:String,
    val desc:String,
    val link:String
):ProvideItemKeys {
    override fun provideKey(): Int {
        return id
    }
}
