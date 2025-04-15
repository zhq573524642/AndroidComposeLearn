package com.zhq.jetpackcomposelearn.ui.screen.mine

import com.zhq.jetpackcomposelearn.base.UserManager
import com.zhq.jetpackcomposelearn.data.UserDTO
import com.zhq.jetpackcomposelearn.data.UserInfoDTO
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.articles.BaseArticleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 14:48
 * Description
 */
@HiltViewModel
class MineViewModelBase @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseArticleViewModel(repo) {

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



}