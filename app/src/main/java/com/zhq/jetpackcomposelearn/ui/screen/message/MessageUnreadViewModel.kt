package com.zhq.jetpackcomposelearn.ui.screen.message

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.data.MessageDTO
import com.zhq.jetpackcomposelearn.repo.MessageRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/14 11:19
 * Description
 */
@HiltViewModel
class MessageUnreadViewModel @Inject constructor(private val repo: MessageRepositoryImpl) :
    BaseViewModel<List<MessageDTO>>() {
    var currentPage: Int = 1
    val messageList = ArrayList<MessageDTO>()


    fun getMessageList(isRefresh: Boolean) {
        showLoading(isClearContent = isRefresh, data = messageList)
        if (isRefresh) {
            currentPage = 1
            messageList.clear()
        }
        launch({
            if (currentPage == 1) {
                handleRequest(repo.getUnreadMessage(currentPage),
                    errorBlock = {
                        showError(msg = it.errorMsg)
                        true
                    }) {
                    messageList.apply { addAll(it.data.datas) }
                    if (messageList.isNotEmpty()) {
                        currentPage = it.data.curPage + 1
                        showContent(data = messageList, isLoadOver = it.data.over)
                    } else {
                        showEmpty(msg = "暂无消息")
                    }
                }
            } else {
                handleRequest(repo.getUnreadMessage(currentPage),
                    errorBlock = {
                        showLoadMoreError(data = messageList)
                        true
                    }) {
                    messageList.apply { addAll(it.data.datas) }
                    if (it.data.over) {
                        showContent(data = messageList, isLoadOver = true)
                        return@handleRequest
                    }
                    currentPage = it.data.curPage + 1
                    showContent(data = messageList, isLoadOver = false)
                }
            }
        })
    }

}