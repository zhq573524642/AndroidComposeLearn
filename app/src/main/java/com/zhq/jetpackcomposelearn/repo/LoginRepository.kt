package com.zhq.jetpackcomposelearn.repo

import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.data.UserDTO
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/7 15:16
 * Description
 */

interface LoginRepository {

    suspend fun login(username: String, password: String): BaseResponse<UserDTO>
    suspend fun register(username: String, password: String, rePassword: String): BaseResponse<Any?>
}

class LoginRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseRepository(apiService), LoginRepository {
    override suspend fun login(username: String, password: String): BaseResponse<UserDTO> {
        return apiService.login(username, password)
    }

    override suspend fun register(
        username: String,
        password: String,
        rePassword: String
    ): BaseResponse<Any?> {
        return apiService.register(username, password, rePassword)
    }

}