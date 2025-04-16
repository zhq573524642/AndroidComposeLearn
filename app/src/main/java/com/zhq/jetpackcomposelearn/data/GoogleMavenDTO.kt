package com.zhq.jetpackcomposelearn.data

import com.zhq.commonlib.ext.ProvideItemKeys

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 16:55
 * Description
 */
private var index: Int = 0

data class GoogleMavenDTO(
    val artifactMap: Map<String, List<ArtifactDTO>>,
    val groupName: String
) : ProvideItemKeys {
    override fun provideKey(): Int {
        return index++
    }
}

data class ArtifactDTO(
    val artifact: String,
    val content: String,
    val group: String,
    val id: Int,
    val version: String
) : ProvideItemKeys {
    override fun provideKey(): Int {
        return id
    }
}
