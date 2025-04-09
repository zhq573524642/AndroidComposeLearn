package com.zhq.jetpackcomposelearn.repo

import android.util.Log
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.commonlib.utils.JsonUtils
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import javax.inject.Inject
import kotlin.math.log

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 15:10
 * Description
 */

interface CourseRepository {
    suspend fun getCourseList(): BaseResponse<List<ArticleDTO>>

    suspend fun getCourseCatalogList(pageIndex:Int,cid:Int):BaseResponse<PageDTO<ArticleDTO>>
}

class CourseRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseRepository(apiService), CourseRepository {
    override suspend fun getCourseList(): BaseResponse<List<ArticleDTO>> {
        val data: BaseResponse<List<ArticleDTO>>? = UserManager.getCourseCacheData()
        if (data != null) {
            return data
        }
        val response = apiService.getCourseList()
        UserManager.setCacheCourseData(JsonUtils.toJson(response))
        return response
    }

    override suspend fun getCourseCatalogList(
        pageIndex: Int,
        cid: Int
    ): BaseResponse<PageDTO<ArticleDTO>> {
        val data=UserManager.getCourseCatalogCacheData(cid, pageIndex)
        if (data!=null){
            return data
        }
        val response=apiService.getCourseCatalogList(pageIndex,cid,1)
        UserManager.setCacheCourseCatalogData(cid,pageIndex,JsonUtils.toJson(response))
        return response
    }

}