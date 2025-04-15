package com.zhq.jetpackcomposelearn.repo

import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.MessageDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/14 11:26
 * Description
 */
private interface MessageRepository {
    suspend fun getReadMessage(pageIndex: Int): BaseResponse<PageDTO<MessageDTO>>

    suspend fun getUnreadMessage(pageIndex: Int): BaseResponse<PageDTO<MessageDTO>>
}

class MessageRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseRepository(apiService), MessageRepository {
    override suspend fun getReadMessage(pageIndex: Int): BaseResponse<PageDTO<MessageDTO>> {
        return apiService.getReadMessage(pageIndex)
    }

    override suspend fun getUnreadMessage(pageIndex: Int): BaseResponse<PageDTO<MessageDTO>> {
        return apiService.getUnreadMessage(pageIndex)
    }

}