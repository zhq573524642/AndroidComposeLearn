package com.zhq.jetpackcomposelearn.ui.screen.mine.collect

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.data.NavigationsDTO
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 10:19
 * Description
 */
@HiltViewModel
class NavigationFuncViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseViewModel<List<NavigationsDTO>>() {

    val navigationList = ArrayList<NavigationsDTO>()
    fun getNavigationData(isRefresh: Boolean) {
        showLoading(isRefresh, data = navigationList)
        if (isRefresh) {
            navigationList.clear()
        }
        launch({
            handleRequest(repo.getNavigationData(),
                errorBlock = {
                    showError(msg = it.errorMsg)
                    true
                }) {
                navigationList.apply { addAll(it.data) }
                if (navigationList.isNotEmpty()) {
                    showContent(data = navigationList, true)
                } else {
                    showEmpty(msg = "暂无数据")
                }
            }
        })
    }
}