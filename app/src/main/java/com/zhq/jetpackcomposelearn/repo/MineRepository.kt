package com.zhq.jetpackcomposelearn.repo

import android.graphics.pdf.PdfDocument.Page
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.commonlib.utils.JsonUtils
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.AppCacheManager
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.CoinInfoDTO
import com.zhq.jetpackcomposelearn.data.GetCoinDTO
import com.zhq.jetpackcomposelearn.data.GoogleMavenDTO
import com.zhq.jetpackcomposelearn.data.NavigationsDTO
import com.zhq.jetpackcomposelearn.data.OfficialAccountDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import com.zhq.jetpackcomposelearn.data.ToolsDTO
import java.util.jar.Attributes.Name
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/11 14:11
 * Description
 */
interface MineRepository {
    suspend fun getMyCollectArticle(page: Int): BaseResponse<PageDTO<ArticleDTO>>

    suspend fun postCollectArticleForExternal(
        title: String,
        author: String,
        link: String
    ): BaseResponse<Unit>

    suspend fun handleEditCollectedArticle(
        articleId: Int,
        title: String,
        author: String,
        link: String
    ): BaseResponse<Unit>

    suspend fun getMyCollectWebsite(): BaseResponse<List<ArticleDTO>>

    suspend fun handleCollectWebsite(name: String, link: String): BaseResponse<Unit>

    suspend fun handleEditWebsite(id: Int, name: String, link: String): BaseResponse<Unit>

    suspend fun deleteCollectWebsite(id: Int): BaseResponse<Unit>

    suspend fun getNavigationData(): BaseResponse<List<NavigationsDTO>>

    suspend fun getOfficialAccountsList(): BaseResponse<List<OfficialAccountDTO>>

    suspend fun getMyCoinInfo(): BaseResponse<CoinInfoDTO>

    suspend fun getUserCoinRankList(pageIndex: Int): BaseResponse<PageDTO<CoinInfoDTO>>

    suspend fun getGetCoinRecordList(pageIndex: Int): BaseResponse<PageDTO<GetCoinDTO>>

    suspend fun getToolsList():BaseResponse<List<ToolsDTO>>

    suspend fun getGoogleMavenPackageName():BaseResponse<List<String>>

    suspend fun queryGoogleMavenPackageName(key:String):BaseResponse<List<GoogleMavenDTO>>
}

class MineRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseRepository(apiService), MineRepository {
    override suspend fun getMyCollectArticle(page: Int): BaseResponse<PageDTO<ArticleDTO>> {
        return apiService.getMyCollectArticle(page)
    }

    override suspend fun postCollectArticleForExternal(
        title: String,
        author: String,
        link: String
    ): BaseResponse<Unit> {
        return apiService.handleCollectArticleForExternal(title, author, link)
    }

    override suspend fun handleEditCollectedArticle(
        articleId: Int,
        title: String,
        author: String,
        link: String
    ): BaseResponse<Unit> {
        return apiService.handleEditCollectedArticle(articleId, title, author, link)
    }

    override suspend fun getMyCollectWebsite(): BaseResponse<List<ArticleDTO>> {
        return apiService.getCollectWebsiteList()
    }

    override suspend fun handleCollectWebsite(name: String, link: String): BaseResponse<Unit> {
        return apiService.handleCollectWebsite(name, link)
    }

    override suspend fun handleEditWebsite(
        id: Int,
        name: String,
        link: String
    ): BaseResponse<Unit> {
        return apiService.handleEditWebsite(id, name, link)
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

    override suspend fun getMyCoinInfo(): BaseResponse<CoinInfoDTO> {
        return apiService.getMyCoinInfo()
    }

    override suspend fun getUserCoinRankList(pageIndex: Int): BaseResponse<PageDTO<CoinInfoDTO>> {
        return apiService.getCoinRankList(pageIndex)
    }

    override suspend fun getGetCoinRecordList(pageIndex: Int): BaseResponse<PageDTO<GetCoinDTO>> {
        return apiService.getGetCoinRecordList(pageIndex)
    }

    override suspend fun getToolsList(): BaseResponse<List<ToolsDTO>> {
        return apiService.getToolsList()
    }

    override suspend fun getGoogleMavenPackageName(): BaseResponse<List<String>> {
        return apiService.getGoogleMavenAllPackageName()
    }

    override suspend fun queryGoogleMavenPackageName(key: String): BaseResponse<List<GoogleMavenDTO>> {
        return apiService.queryGoogleMavenPackageName(key)
    }

}