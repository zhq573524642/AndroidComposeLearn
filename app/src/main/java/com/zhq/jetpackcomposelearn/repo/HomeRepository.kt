package com.zhq.jetpackcomposelearn.repo

import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.BannerDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 16:07
 * Description
 */

interface HomeRepository {
    suspend fun getBannerData():BaseResponse<List<BannerDTO>>
    suspend fun getHomeTopArticleList(): BaseResponse<List<ArticleDTO>>
    suspend fun getHomeArticleList(
        pageIndex: Int,
        pageSize: Int
    ): BaseResponse<PageDTO<ArticleDTO>>
}

class HomeRepositoryImpl @Inject constructor(private val apiService: ApiService) : BaseRepository(apiService),HomeRepository {
    override suspend fun getBannerData(): BaseResponse<List<BannerDTO>> {
        return apiService.getBannerData()
    }

    override suspend fun getHomeTopArticleList(): BaseResponse<List<ArticleDTO>> {
        return apiService.getArticleTopList()
    }

    override suspend fun getHomeArticleList(
        pageIndex: Int,
        pageSize: Int
    ): BaseResponse<PageDTO<ArticleDTO>> {
        return apiService.getHomeArticles(pageIndex,pageSize)
    }


}