package com.zhq.jetpackcomposelearn.base

import com.tencent.mmkv.MMKV

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 17:18
 * Description
 */
object AppCacheManager {
    private const val KEY_FIRST_OPEN = "first_open_app"

    private val mmkv by lazy { MMKV.defaultMMKV() }

    /** 存储是否首次使用APP */
    fun saveFirstOpen(isFirstOpen: Boolean): Boolean {
        return mmkv.encode(KEY_FIRST_OPEN, isFirstOpen)
    }

    /** 是否首次使用APP */
    fun isFirstOpen(): Boolean {
        return mmkv.decodeBool(KEY_FIRST_OPEN, false)
    }
}