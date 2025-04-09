package com.zhq.jetpackcomposelearn.repo

import android.util.Log
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import javax.inject.Inject
import kotlin.math.log

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/2 17:47
 * Description
 */

interface ProjectRepository {
    suspend fun getProjectsType(): BaseResponse<List<ArticleDTO>>

    suspend fun getProjectsList(cid: Int, pageIndex: Int): BaseResponse<PageDTO<ArticleDTO>>
}

private const val TAG = "ProjectRepository"
class ProjectRepositoryImp @Inject constructor(private val apiService: ApiService) :
    BaseRepository(apiService), ProjectRepository {

    override suspend fun getProjectsType(): BaseResponse<List<ArticleDTO>> {
        return apiService.getProjectsType()
    }

    override suspend fun getProjectsList(
        cid: Int,
        pageIndex: Int
    ): BaseResponse<PageDTO<ArticleDTO>> {
        return apiService.getProjectsList(cid, pageIndex)
    }

}