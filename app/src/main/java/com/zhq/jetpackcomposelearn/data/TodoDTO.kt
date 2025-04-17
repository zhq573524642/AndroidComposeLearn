package com.zhq.jetpackcomposelearn.data

import com.zhq.commonlib.ext.ProvideItemKeys

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/17 10:24
 * Description
 */
data class TodoDTO(
    val id:Int,
    var title:String,
    var content:String,
    var dateStr:String="",
    var priority:Int,
    var status:Int,
    var type:Int,
    val completeDateStr:String="",
    val userId:String=""
):ProvideItemKeys {
    override fun provideKey(): Int {
        return id
    }
}
