package com.zhq.jetpackcomposelearn.data

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 15:01
 * Description
 */
data class ShareUserDTO(
    val coinInfo: CoinInfoDTO,
    val shareArticles: PageDTO<ArticleDTO>
)
