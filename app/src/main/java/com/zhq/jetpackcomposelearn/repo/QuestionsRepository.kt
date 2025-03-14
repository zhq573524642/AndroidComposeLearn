package com.zhq.jetpackcomposelearn.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/7 17:07
 * Description
 */

interface QuestionsRepository {
    fun getQuestionsList(): Flow<PagingData<ArticleDTO>>
}

class QuestionsRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseRepository(apiService), QuestionsRepository {

    override fun getQuestionsList(): Flow<PagingData<ArticleDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, initialLoadSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                DataPagingSource(apiService)
            }
        ).flow
    }

}

class DataPagingSource(private val apiService: ApiService) : PagingSource<Int, ArticleDTO>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDTO> {
        return try {
            val page = params.key ?: 0//当前页，默认第一页
            val pageSize = params.loadSize//每页数据条数
            val response = apiService.getQuestionsList(page, pageSize)
            val data = response.data
            val repoItems: List<ArticleDTO> =
                data?.datas ?: emptyList()
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}