package com.zhq.jetpackcomposelearn.ui.screen.mine.coin

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.data.GetCoinDTO
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 15:38
 * Description
 */
@HiltViewModel
class GetCoinViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseViewModel<List<GetCoinDTO>>() {

    val recordList = ArrayList<GetCoinDTO>()
    var currentPage: Int = 1

    fun getGetCoinRecordList(isRefresh: Boolean) {
        showLoading(isClearContent = isRefresh, data = recordList)
        if (isRefresh) {
            currentPage = 1
            recordList.clear()
        }
        launch({
            if (currentPage == 1) {
                handleRequest(repo.getGetCoinRecordList(currentPage),
                    errorBlock = {
                        showError(msg = it.errorMsg)
                        true
                    }) {
                    recordList.addAll(it.data.datas)
                    if (recordList.isNotEmpty()) {
                        currentPage = it.data.curPage + 1
                        showContent(data = recordList, isLoadOver = it.data.over)
                    } else {
                        showEmpty()
                    }
                }
            } else {
                handleRequest(repo.getGetCoinRecordList(currentPage),
                    errorBlock = {
                        showLoadMoreError(data = recordList, msg = it.errorMsg)
                        true
                    }) {
                    recordList.addAll(it.data.datas)
                    if (it.data.over) {
                        showContent(data = recordList, isLoadOver = true)
                        return@handleRequest
                    }
                    currentPage = it.data.curPage + 1
                    showContent(data = recordList, isLoadOver = false)
                }
            }
        })
    }
}