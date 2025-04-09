package com.zhq.jetpackcomposelearn.repo

import android.util.Log
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import com.zhq.jetpackcomposelearn.ui.screen.systems.model.SystemsDTO
import javax.inject.Inject
import kotlin.math.log

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/7 17:18
 * Description
 */
private const val TAG = "SystemsRepository"

interface SystemsRepository {
    suspend fun getSystemsList(): BaseResponse<List<SystemsDTO>>
    suspend fun getSystemChildList(pageIndex: Int, cid: Int): BaseResponse<PageDTO<ArticleDTO>>
}

class SystemsRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseRepository(apiService), SystemsRepository {
    override suspend fun getSystemsList(): BaseResponse<List<SystemsDTO>> {
        return apiService.getSystemsList()
    }

    override suspend fun getSystemChildList(
        pageIndex: Int,
        cid: Int
    ): BaseResponse<PageDTO<ArticleDTO>> {
        return apiService.getSystemChildList(pageIndex, cid)
    }

}