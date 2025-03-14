package com.zhq.commonlib.base

import android.os.Bundle
import androidx.activity.ComponentActivity

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 15:49
 * Description
 */
open class BaseActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    protected open fun initObserver() {

    }
}