package com.zhq.jetpackcomposelearn.ui.screen.harmony.model

import com.zhq.jetpackcomposelearn.data.ArticleDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/18 14:05
 * Description
 */
data class HarmonyDTO(
    val links: HarmonyChildDTO,
    val open_sources: HarmonyChildDTO,
    val tools: HarmonyChildDTO
)

data class HarmonyChildDTO(
    val articleList: List<ArticleDTO>,
    val courseId: Int,
    val name: String
)
