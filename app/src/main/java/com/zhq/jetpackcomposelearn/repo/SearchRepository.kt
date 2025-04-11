package com.zhq.jetpackcomposelearn.repo

import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.SearchHotKeyDTO
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/11 10:29
 * Description
 */
interface SearchRepository {
    suspend fun getSearchHotKey():BaseResponse<List<SearchHotKeyDTO>>

    suspend fun getCommonWebsite():BaseResponse<List<SearchHotKeyDTO>>
}

class SearchRepositoryImpl @Inject constructor(private val apiService: ApiService) :BaseRepository(apiService),SearchRepository{
    override suspend fun getSearchHotKey(): BaseResponse<List<SearchHotKeyDTO>> {
        return apiService.getSearchHotKeys()
    }

    override suspend fun getCommonWebsite(): BaseResponse<List<SearchHotKeyDTO>> {
        return apiService.getCommonWebsite()
    }

}
