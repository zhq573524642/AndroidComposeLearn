package com.zhq.jetpackcomposelearn.ui.screen.mine.coin

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.data.CoinInfoDTO
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.ArrayList
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 14:58
 * Description
 */
@HiltViewModel
class CoinViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseViewModel<List<CoinInfoDTO>>() {

    val coinList = ArrayList<CoinInfoDTO>()
    var currentPage: Int = 1
    private val _myCoinInfo = MutableStateFlow<CoinInfoDTO?>(null)
    val myCoinInfo: StateFlow<CoinInfoDTO?> get() = _myCoinInfo

    fun getMyCoinInfo() {
        launch({
            handleRequest(repo.getMyCoinInfo()) {
                _myCoinInfo.value = it.data
            }
        })
    }

    fun getUserCoinRankList(isRefresh: Boolean) {
        showLoading(isClearContent = isRefresh, data = coinList)
        if (isRefresh) {
            currentPage = 1
            coinList.clear()
        }
        launch({
            if (currentPage == 1) {
                handleRequest(repo.getUserCoinRankList(currentPage),
                    errorBlock = {
                        showError(msg = it.errorMsg)
                        true
                    }) {
                    coinList.addAll(it.data.datas)
                    if (coinList.isNotEmpty()) {
                        currentPage = it.data.curPage + 1
                        showContent(data = coinList, isLoadOver = it.data.over)
                    } else {
                        showEmpty()
                    }
                }
            } else {
                handleRequest(repo.getUserCoinRankList(currentPage),
                    errorBlock = {
                        showLoadMoreError(data = coinList, msg = it.errorMsg)
                        true
                    }) {
                    coinList.addAll(it.data.datas)
                    if (it.data.over) {
                        showContent(data = coinList, true)
                        return@handleRequest
                    }
                    currentPage = it.data.curPage + 1
                    showContent(data = coinList, false)
                }
            }
        })
    }
}