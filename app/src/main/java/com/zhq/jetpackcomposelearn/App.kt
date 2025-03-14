package com.zhq.jetpackcomposelearn

import com.zhq.commonlib.base.BaseApp
import com.zhq.commonlib.utils.ToastUtils
import com.zhq.jetpackcomposelearn.base.AppViewModel
import dagger.hilt.android.HiltAndroidApp

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 16:36
 * Description
 */
@HiltAndroidApp
class App : BaseApp() {

    companion object {
        lateinit var appViewModel: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        appViewModel = getAppViewModelProvider()[AppViewModel::class.java]
        ToastUtils.init(this)
    }
}