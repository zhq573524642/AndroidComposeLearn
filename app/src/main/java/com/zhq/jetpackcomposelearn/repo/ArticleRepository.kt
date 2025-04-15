package com.zhq.jetpackcomposelearn.repo

import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import com.zhq.jetpackcomposelearn.data.ShareUserDTO
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 13:41
 * Description
 */
interface ArticleRepository {
    suspend fun getArticlesForOfficialAccount(
        id: Int,
        pageIndex: Int
    ): BaseResponse<PageDTO<ArticleDTO>>

    suspend fun getSquareArticleList(pageIndex: Int):BaseResponse<PageDTO<ArticleDTO>>

    suspend fun getArticleListForShareUser(pageIndex: Int,id: Int):BaseResponse<ShareUserDTO>
}

class ArticleRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseRepository(apiService), ArticleRepository {
   override suspend fun getArticlesForOfficialAccount(
      id: Int,
      pageIndex: Int
   ): BaseResponse<PageDTO<ArticleDTO>> {
      return apiService.getArticleForOfficialAccount(id, pageIndex)
   }

    override suspend fun getSquareArticleList(pageIndex: Int): BaseResponse<PageDTO<ArticleDTO>> {
        return apiService.getSquareArticleList(pageIndex)
    }

    override suspend fun getArticleListForShareUser(
        pageIndex: Int,
        id: Int
    ): BaseResponse<ShareUserDTO> {
        return apiService.getArticleListForShareUser(pageIndex, id)
    }

}