package com.zhq.commonlib.utils

import android.app.Application
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.zhq.commonlib.utils.ToastUtils.showToast

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/13 14:30
 * Description
 */
object ToastUtils {
    lateinit var mContext: Application
    fun init(application: Application) {
        mContext = application
    }

    fun String.showToast() {
        Toast.makeText(mContext.applicationContext, this, Toast.LENGTH_SHORT).show()
    }

    fun Int.showToast() {
        Toast.makeText(mContext.applicationContext, this, Toast.LENGTH_SHORT).show()
    }

    fun String.showLongToast() {
        Toast.makeText(mContext.applicationContext, this, Toast.LENGTH_LONG).show()
    }

    fun Int.showLongToast() {
        Toast.makeText(mContext.applicationContext, this, Toast.LENGTH_LONG).show()
    }

    fun String.showCenterToast() {
        Toast.makeText(mContext.applicationContext, this, Toast.LENGTH_SHORT)
            .apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }

    }

    fun Int.showCenterToast() {
        Toast.makeText(mContext.applicationContext, this, Toast.LENGTH_SHORT)
            .apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }

    }

    fun String.showCenterLongToast() {
        Toast.makeText(mContext.applicationContext, this, Toast.LENGTH_LONG)
            .apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }

    }

    fun Int.showCenterLongToast() {
        Toast.makeText(mContext.applicationContext, this, Toast.LENGTH_LONG)
            .apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }

    }
}