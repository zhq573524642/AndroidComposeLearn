package com.zhq.commonlib.http.interceptor

import com.zhq.commonlib.utils.LogUtil
import okhttp3.logging.HttpLoggingInterceptor

/**
 * okhttp 日志拦截器
 */
val logInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        // 使用自己的日志工具接管
        LogUtil.d(message)

    }
    // 注意要生成BuildConfig类，就必须在gradle中配置buildConfig为true
}).setLevel(if (true) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC)