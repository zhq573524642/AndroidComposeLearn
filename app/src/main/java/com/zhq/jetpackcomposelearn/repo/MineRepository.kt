package com.zhq.jetpackcomposelearn.repo

import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.commonlib.utils.JsonUtils
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.AppCacheManager
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.NavigationsDTO
import com.zhq.jetpackcomposelearn.data.OfficialAccountDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/11 14:11
 * Description
 */
interface MineRepository {
    suspend fun getMyCollectArticle(page: Int): BaseResponse<PageDTO<ArticleDTO>>

    suspend fun getMyCollectWebsite(): BaseResponse<List<ArticleDTO>>

    suspend fun deleteCollectWebsite(id: Int): BaseResponse<Unit>

    suspend fun getNavigationData(): BaseResponse<List<NavigationsDTO>>

    suspend fun getOfficialAccountsList(): BaseResponse<List<OfficialAccountDTO>>
}

class MineRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseRepository(apiService), MineRepository {
    override suspend fun getMyCollectArticle(page: Int): BaseResponse<PageDTO<ArticleDTO>> {
        return apiService.getMyCollectArticle(page)
    }

    override suspend fun getMyCollectWebsite(): BaseResponse<List<ArticleDTO>> {
        return apiService.getCollectWebsiteList()
    }

    override suspend fun deleteCollectWebsite(id: Int): BaseResponse<Unit> {
        return apiService.deleteCollectWebsite(id)
    }

    override suspend fun getNavigationData(): BaseResponse<List<NavigationsDTO>> {
        val data = AppCacheManager.getNavigationListCacheData()
        if (data != null) {
            return data
        }
        val result = apiService.getNavigationData()
        AppCacheManager.setCacheNavigationListData(JsonUtils.toJson(result))
        return result
    }

    override suspend fun getOfficialAccountsList(): BaseResponse<List<OfficialAccountDTO>> {
        val data = AppCacheManager.getOfficialAccountCacheData()
        if (data != null) return data
        val result = apiService.getOfficialAccountsList()
        AppCacheManager.setCacheOfficialAccountListData(JsonUtils.toJson(result))
        return result
    }

}