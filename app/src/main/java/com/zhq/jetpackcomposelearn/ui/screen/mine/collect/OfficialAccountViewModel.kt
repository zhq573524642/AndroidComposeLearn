package com.zhq.jetpackcomposelearn.ui.screen.mine.collect

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.data.OfficialAccountDTO
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/15 11:21
 * Description
 */
@HiltViewModel
class OfficialAccountViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseViewModel<List<OfficialAccountDTO>>() {

    val accountList = ArrayList<OfficialAccountDTO>()

    fun getOfficialAccountList(isRefresh: Boolean) {
        showLoading(isClearContent = isRefresh, data = accountList)
        if (isRefresh) {
            accountList.clear()
        }
        launch({
            handleRequest(repo.getOfficialAccountsList(),
                errorBlock = {
                    showError(msg = it.errorMsg)
                    true
                }) {
                accountList.apply { addAll(it.data) }
                if (accountList.isNotEmpty()) {
                    showContent(data = accountList, true)
                } else {
                    showEmpty()
                }
            }
        })
    }

}