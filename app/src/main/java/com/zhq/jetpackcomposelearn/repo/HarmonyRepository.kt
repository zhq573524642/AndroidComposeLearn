package com.zhq.jetpackcomposelearn.repo

import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.api.ApiService
import com.zhq.jetpackcomposelearn.base.BaseRepository
import com.zhq.jetpackcomposelearn.ui.screen.harmony.model.HarmonyDTO
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/18 14:10
 * Description
 */

interface HarmonyRepository{

    suspend fun getHarmonyData():BaseResponse<HarmonyDTO>
}

class HarmonyRepositoryImpl @Inject constructor(private val apiService: ApiService) :BaseRepository(apiService),HarmonyRepository{
    override suspend fun getHarmonyData(): BaseResponse<HarmonyDTO> {
        return apiService.getHarmonyData()
    }
}