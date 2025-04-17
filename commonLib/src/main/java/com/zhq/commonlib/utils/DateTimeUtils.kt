package com.zhq.commonlib.utils

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/17 15:11
 * Description
 */
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 时间戳转换工具类
 * 1. 支持秒级/毫秒级时间戳自动识别
 * 2. 支持时区设置（默认系统时区）
 * 3. 内置常用格式，支持自定义格式
 */
object DateTimeUtils {

    /**
     * 将时间戳转换为 LocalDateTime 对象
     * @param timestamp 时间戳（支持秒级10位/毫秒级13位）
     * @param zoneId 时区（默认系统时区）
     */
    @JvmStatic
    fun toLocalDateTime(timestamp: Long, zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime {
        val instant = if (timestamp.toString().length == 10) {
            Instant.ofEpochSecond(timestamp)
        } else {
            Instant.ofEpochMilli(timestamp)
        }
        return instant.atZone(zoneId).toLocalDateTime()
    }

    /**
     * 格式化日期（yyyy-MM-dd）
     */
    @JvmStatic
    fun formatDate(timestamp: Long, zoneId: ZoneId = ZoneId.systemDefault()) =
        format(timestamp, "yyyy-MM-dd", zoneId)

    /**
     * 格式化时间（HH:mm:ss）
     */
    @JvmStatic
    fun formatTime(timestamp: Long, zoneId: ZoneId = ZoneId.systemDefault()) =
        format(timestamp, "HH:mm:ss", zoneId)

    /**
     * 格式化日期时间（yyyy-MM-dd HH:mm:ss）
     */
    @JvmStatic
    fun formatDateTime(timestamp: Long, zoneId: ZoneId = ZoneId.systemDefault()) =
        format(timestamp, "yyyy-MM-dd HH:mm:ss", zoneId)

    /**
     * 自定义格式化时间戳
     * @param pattern 格式模式（例如："yyyy/MM/dd HH:mm:ss"）
     */
    @JvmStatic
    fun format(timestamp: Long, pattern: String, zoneId: ZoneId = ZoneId.systemDefault()): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return toLocalDateTime(timestamp, zoneId).format(formatter)
    }
}