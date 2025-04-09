package com.zhq.jetpackcomposelearn.ext

import androidx.compose.ui.graphics.Color

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 14:22
 * Description
 */
// 扩展函数：判断颜色是否为亮色
fun Color.isLightColor(): Boolean {
    val luminance = 0.2126f * red + 0.7152f * green + 0.0722f * blue
    return luminance > 0.5f
}