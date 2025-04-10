package com.zhq.jetpackcomposelearn.ui.screen.systems.model

import com.zhq.commonlib.ext.ProvideItemKeys

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 10:18
 * Description
 */
data class SystemsDTO(
    val id: Int,
    val name: String,
    val children: List<SystemsChildDTO>,
) : ProvideItemKeys {
    override fun provideKey(): Int {
        return id
    }
}

data class SystemsChildDTO(
    val name: String,
    val id: Int
) : ProvideItemKeys {
    override fun provideKey(): Int {
        return id
    }
}