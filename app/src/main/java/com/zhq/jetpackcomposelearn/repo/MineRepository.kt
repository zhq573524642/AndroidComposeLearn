package com.zhq.jetpackcomposelearn.repo

import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/11 14:11
 * Description
 */
interface MineRepository {
    suspend fun getMyCollectArticle(page:Int):BaseResponse<PageDTO<ArticleDTO>>
}

class MineRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseRepository(apiService), MineRepository {
    override suspend fun getMyCollectArticle(page: Int): BaseResponse<PageDTO<ArticleDTO>> {
        return apiService.getMyCollectArticle(page)
    }

}