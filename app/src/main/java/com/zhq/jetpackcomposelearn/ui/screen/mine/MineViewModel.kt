package com.zhq.jetpackcomposelearn.ui.screen.mine

import androidx.compose.runtime.collectAsState
import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.App
import com.zhq.jetpackcomposelearn.base.AppViewModel
import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.UserDTO
import com.zhq.jetpackcomposelearn.data.UserInfoDTO
import com.zhq.jetpackcomposelearn.repo.MineRepository
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.ArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 14:48
 * Description
 */
@HiltViewModel
class MineViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
    ArticleViewModel(repo) {

    fun getUserName(): String {
        val user: UserDTO = UserManager.getUser()?.userInfo ?: return "请登录"
        return if (user.nickname.isNotEmpty()) user.nickname else user.username
    }

    fun getUserLevel(): String {
        val user: UserInfoDTO = UserManager.getUser() ?: return "lv：——"
        return "lv：${user.coinInfo.level}"
    }

    fun getUserCoinCount(): String {
        val user: UserInfoDTO = UserManager.getUser() ?: return "0"
        return user.coinInfo.coinCount
    }

    fun getMyCollectArticle(isRefresh: Boolean = true) {
        showLoading(isRefresh, data = articleList)
        launch({
            if (isRefresh) {
                articleList.clear()
                currentPage = 0
            }
            if (currentPage == 0) {
                handleRequest(repo.getMyCollectArticle(currentPage),
                    errorBlock = {
                        showError(it.errorMsg)
                        true
                    }) {
                    articleList.apply { addAll(it.data.datas) }
                    if (articleList.isEmpty()) {
                        showEmpty()
                    } else {
                        showContent(data = articleList, it.data.over)
                        currentPage = it.data.curPage
                    }
                }
            } else {
                handleRequest(repo.getMyCollectArticle(currentPage),
                    errorBlock = {
                        showLoadMoreError(data = articleList)
                        true
                    }){
                    articleList.apply { addAll(it.data.datas) }
                    if (it.data.over){
                        showContent(data = articleList, isLoadOver = true)
                        return@handleRequest
                    }
                    showContent(data = articleList,false)
                }
            }

        })
    }

}