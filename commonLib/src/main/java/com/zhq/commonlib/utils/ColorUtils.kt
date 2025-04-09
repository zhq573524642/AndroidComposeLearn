package com.zhq.commonlib.utils

import kotlin.random.Random

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 10:03
 * Description
 */
object ColorUtils {

    /**
     * 生成不透明的随机颜色值 (格式: 0xAARRGGBB，AA 固定为 FF)
     * @return 颜色字符串，例如 "0xFF262626"
     */
    fun generateRandomColorString(): String {
        val red = Random.nextInt(256)
        val green = Random.nextInt(256)
        val blue = Random.nextInt(256)
        return "0xFF%02X%02X%02X".format(red, green, blue)
    }

    /**
     * 生成带透明度的随机颜色值 (格式: 0xAARRGGBB)
     * @return 颜色字符串，例如 "0x80262626"
     */
    fun generateRandomColorStringWithAlpha(): String {
        val alpha = Random.nextInt(256)
        val red = Random.nextInt(256)
        val green = Random.nextInt(256)
        val blue = Random.nextInt(256)
        return "0x%02X%02X%02X%02X".format(alpha, red, green, blue)
    }

    // 生成柔和颜色（Pastel Color）
    fun generatePastelColorString(): String {
        val red = Random.nextInt(128, 256)
        val green = Random.nextInt(128, 256)
        val blue = Random.nextInt(128, 256)
        return "0xFF%02X%02X%02X".format(red, green, blue)
    }

    // 生成随机颜色 Int 值（Android 兼容）
    fun generateRandomColorInt(): Int {
        return 0xFF000000.toInt() or
                (Random.nextInt(256) shl 16) or
                (Random.nextInt(256) shl 8) or
                Random.nextInt(256)
    }

    /**
     * 生成不透明的随机颜色值 (格式: 0xAARRGGBB，AA 固定为 FF)
     * @return 颜色 Long 值，例如 0xFF262626
     *
     * Alpha通道: alpha.toLong() shl 24  // 左移24位到高位
     * Red通道:   red.toLong() shl 16   // 左移16位
     * Green通道: green.toLong() shl 8  // 左移8位
     * Blue通道:  blue.toLong()         // 不移位
     */
    fun generateRandomColorLong(): Long {
        val red = Random.nextInt(256)
        val green = Random.nextInt(256)
        val blue = Random.nextInt(256)
        return 0xFF000000L or
                (red.toLong() shl 16) or
                (green.toLong() shl 8) or
                blue.toLong()
    }

    /**
     * 生成带透明度的随机颜色值 (格式: 0xAARRGGBB)
     * @return 颜色 Long 值，例如 0x80262626
     */
    fun generateRandomColorLongWithAlpha(): Long {
        val alpha = Random.nextInt(256)
        val red = Random.nextInt(256)
        val green = Random.nextInt(256)
        val blue = Random.nextInt(256)
        return (alpha.toLong() shl 24) or
                (red.toLong() shl 16) or
                (green.toLong() shl 8) or
                blue.toLong()
    }

    // 生成柔和色（Pastel Color）
    fun generatePastelColorLong(): Long {
        val red = Random.nextInt(128, 256)
        val green = Random.nextInt(128, 256)
        val blue = Random.nextInt(128, 256)
        return 0xFF000000L or
                (red.toLong() shl 16) or
                (green.toLong() shl 8) or
                blue.toLong()
    }

    // 转换为 Android 颜色 Int（自动截取低32位）
    fun toAndroidColor(color: Long): Int = color.toInt()

}