package com.zhq.jetpackcomposelearn.data

import com.zhq.commonlib.ext.ProvideItemKeys

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 11:18
 * Description
 */
data class OfficialAccountDTO(
    val id: Int,
    val name: String
) : ProvideItemKeys {
    override fun provideKey(): Int {
        return id
    }
}
