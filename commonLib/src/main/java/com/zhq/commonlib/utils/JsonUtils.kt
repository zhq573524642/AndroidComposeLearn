package com.zhq.commonlib.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.reflect.Type
import kotlin.reflect.typeOf

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/8 10:23
 * Description
 */
object JsonUtils {

    // 配置 Gson 实例（可自定义）
     val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss") // 日期格式
        .serializeNulls()                     // 序列化 null 值
        .disableHtmlEscaping()                // 禁用 HTML 转义
        .create()

    /**
     * 将对象转为 JSON 字符串
     * @param obj 任意对象
     * @return JSON 字符串，失败返回空字符串
     */
    fun toJson(obj: Any?): String {
        return gson.toJson(obj ?: return "")
    }

    /**
     * 将 JSON 字符串转为指定类型对象
     * @param json JSON 字符串
     * @return 目标对象，解析失败返回 null
     */
    inline fun <reified T> fromJson(json: String?): T? {
        json ?: return null
        return try {
            gson.fromJson(json, typeOf<T>())
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 获取泛型的 Type 类型
     */
    inline fun <reified T> typeOf(): Type = object : TypeToken<T>() {}.type

    /**
     * 安卓专用：支持带默认值的 Kotlin 数据类
     */
    inline fun <reified T> fromJsonWithDefault(json: String?): T? {
        json ?: return null
        return try {
            gson.fromJson(json, T::class.java)
        } catch (e: Exception) {
            null
        }
    }

    val json = Json {
        ignoreUnknownKeys = true // 忽略 JSON 中多余的字段
        coerceInputValues = true // 尝试转换无效值（如字符串转数字失败时用默认值）
    }

    fun toJsonForKts(obj: Any): String = json.encodeToString(obj)

    inline fun <reified T> fromJsonForKts(jsonStr: String): T = json.decodeFromString(jsonStr)
}