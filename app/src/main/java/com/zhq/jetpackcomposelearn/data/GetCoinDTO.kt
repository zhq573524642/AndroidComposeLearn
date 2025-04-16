package com.zhq.jetpackcomposelearn.data

import com.zhq.commonlib.ext.ProvideItemKeys

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 15:39
 * Description
 */
data class GetCoinDTO(
    val id:Int,
    val coinCount:Int,
    val desc:String
):ProvideItemKeys {
    override fun provideKey(): Int {
        return id
    }
}
