package com.zhq.jetpackcomposelearn.data

import com.zhq.commonlib.ext.ProvideItemKeys

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 10:21
 * Description
 */
data class NavigationsDTO(
    val cid:Int,
    val name:String,
    val articles:List<ArticleDTO>
):ProvideItemKeys {
    override fun provideKey(): Int {
        return cid
    }
}
