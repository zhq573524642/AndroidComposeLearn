package com.zhq.jetpackcomposelearn.base

import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 16:06
 * Description
 */
open class BaseRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun handleCollectArticle(
        isCollect: Boolean,
        id: Int,
        isFromMyCollection: Boolean = false,//是否来自我的收藏页面的取消收藏操作
        originId: Int = -1//不存在就默认-1
    ): BaseResponse<Unit> {
        return if (isFromMyCollection) {
            apiService.handleUnCollectArticleForMine(id = id, originId = originId)
        } else {
            if (isCollect) {
                apiService.handleCollectArticle(id)
            } else {
                apiService.handleUncollectArticle(id)
            }
        }

    }


}