package com.zhq.commonlib.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zhq.commonlib.base.BaseApp.Companion.appContext
import com.zhq.commonlib.http.interceptor.CacheInterceptor
import com.zhq.commonlib.http.interceptor.logInterceptor
import com.zhq.commonlib.utils.LogUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 16:17
 * Description
 */
object RetrofitManager {

    /** 请求超时时间 */
    private const val TIME_OUT_SECONDS = 10

    /** 请求cookie */
    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(appContext)
        )
    }

    /** 请求根地址 */
    private val BASE_URL = "https://www.wanandroid.com/"

    /** OkHttpClient相关配置 */
    fun getOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        // 请求过滤器
        .addInterceptor(logInterceptor)
        //设置缓存配置,缓存最大10M,设置了缓存之后可缓存请求的数据到data/data/包名/cache/net_cache目录中
        .cache(Cache(File(appContext.cacheDir, "net_cache"), 10 * 1024 * 1024))
        //添加缓存拦截器 可传入缓存天数
        .addInterceptor(CacheInterceptor(30))
        // 请求超时时间
        .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
        .cookieJar(cookieJar)
        .build()


    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        // 使用Moshi更适合Kotlin
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}