package com.zhq.jetpackcomposelearn.ui.screen.systems

import android.util.Log
import com.zhq.commonlib.base.BaseViewModel
import com.zhq.commonlib.utils.JsonUtils
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.repo.ProjectRepositoryImp
import com.zhq.jetpackcomposelearn.repo.SystemsRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.ArticleViewModel
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
            emitUiState(
                showLoading = false,
                data = systemsList,
                showLoadingMore = false,
                noMoreData = false
            )
        } else {
            getSystemsList()
        }
    }


    fun getSystemsList(isRefreshing: Boolean = true) {
        emitUiState(
            showLoading = isRefreshing, data = systemsList,
            showLoadingMore = false,
            noMoreData = true
        )
        launch({
            if (isRefreshing) {
                systemsList.clear()
            }
            handleRequest(repo.getSystemsList()) {
                UserManager.setCacheSystemsData(JsonUtils.toJson(it.data))
                emitUiState(
                    showLoading = false,
                    data = systemsList.apply { addAll(it.data) },
                    showLoadingMore = false,
                    noMoreData = true
                )
            }
        })
    }
}