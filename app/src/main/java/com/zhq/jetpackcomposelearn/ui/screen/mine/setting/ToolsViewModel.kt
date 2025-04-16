package com.zhq.jetpackcomposelearn.ui.screen.mine.setting

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.data.ToolsDTO
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 16:07
 * Description
 */
@HiltViewModel
class ToolsViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseViewModel<List<ToolsDTO>>() {

    val toolsList = ArrayList<ToolsDTO>()

    fun getToolsList(isRefresh: Boolean) {
        showLoading(isRefresh, data = toolsList)
        launch({
            handleRequest(repo.getToolsList(),
                errorBlock = {
                    showError(msg = it.errorMsg)
                    true
                }) {
                toolsList.addAll(it.data)
                showContent(data = toolsList, true)
            }
        })
    }
}