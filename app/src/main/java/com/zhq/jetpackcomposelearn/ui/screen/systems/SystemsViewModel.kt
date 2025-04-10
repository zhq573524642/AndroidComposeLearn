package com.zhq.jetpackcomposelearn.ui.screen.systems

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.commonlib.utils.JsonUtils
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.repo.SystemsRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.systems.model.SystemsDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/7 17:17
 * Description
 */

@HiltViewModel
class SystemsViewModel @Inject constructor(private val repo: SystemsRepositoryImpl) :
    BaseViewModel<List<SystemsDTO>>() {
    val systemsList = ArrayList<SystemsDTO>()

    init {
        val list: List<SystemsDTO>? = UserManager.getSystemsCacheData()
        if (list?.isNotEmpty() == true) {
            systemsList.clear()
            systemsList.addAll(list)
            showContent(data = systemsList, true)
        } else {
            getSystemsList()
        }
    }


    fun getSystemsList(isRefreshing: Boolean = true) {
        showLoading(isRefreshing, data = systemsList)
        launch({
            if (isRefreshing) {
                systemsList.clear()
            }
            handleRequest(repo.getSystemsList(),
                errorBlock = {
                    showError(msg = it.errorMsg)
                    true
                }) {
                UserManager.setCacheSystemsData(JsonUtils.toJson(it.data))
                systemsList.apply { addAll(it.data) }
                if (systemsList.isEmpty()) {
                    showEmpty(msg = "暂无数据")
                } else {
                    showContent(data = systemsList, isLoadOver = true)
                }
            }
        })
    }
}