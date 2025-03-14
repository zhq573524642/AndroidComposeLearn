package com.zhq.commonlib.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 16:05
 * Description
 */
open class BaseApp : Application(), ViewModelStoreOwner {
    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        var appContext: Context by Delegates.notNull()
        lateinit var baseAppViewModel: BaseAppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        //获取applicationContext
        appContext = applicationContext
        baseAppViewModel = getAppViewModelProvider()[BaseAppViewModel::class.java]
        MMKV.initialize(this)
    }

    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, getAppViewModelFactory())
    }

    private fun getAppViewModelFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    override val viewModelStore: ViewModelStore
        get() = ViewModelStore()
}