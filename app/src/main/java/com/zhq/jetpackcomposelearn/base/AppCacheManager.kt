package com.zhq.jetpackcomposelearn.base

import com.tencent.mmkv.MMKV
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.commonlib.utils.JsonUtils
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.NavigationsDTO
import com.zhq.jetpackcomposelearn.data.OfficialAccountDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import com.zhq.jetpackcomposelearn.ui.screen.systems.model.SystemsDTO

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 17:18
 * Description
 */
object AppCacheManager {
    private const val KEY_FIRST_OPEN = "first_open_app"
    private const val DATA_SYSTEMS = "data_systems"
    private const val DATA_COURSE_LIST = "data_course_list"
    private const val DATA_NAVIGATION_LIST = "data_navigation_list"
    private const val DATA_OFFICIAL_ACCOUNT_LIST="data_official_account_list"

    private val mmkv by lazy { MMKV.defaultMMKV() }

    /** 存储是否首次使用APP */
    fun saveFirstOpen(isFirstOpen: Boolean): Boolean {
        return mmkv.encode(KEY_FIRST_OPEN, isFirstOpen)
    }

    /** 是否首次使用APP */
    fun isFirstOpen(): Boolean {
        return mmkv.decodeBool(KEY_FIRST_OPEN, false)
    }

    //数据
    fun setCacheSystemsData(json: String) {
        mmkv.encode(DATA_SYSTEMS, json)
    }

    fun getSystemsCacheData(): List<SystemsDTO>? {
        val json = mmkv.decodeString(DATA_SYSTEMS)
        if (json?.isEmpty() == true) return emptyList()
        return JsonUtils.fromJson<List<SystemsDTO>>(json)
    }

    fun setCacheCourseData(json: String) {
        mmkv.encode(DATA_COURSE_LIST, json)
    }

    fun getCourseCacheData(): BaseResponse<List<ArticleDTO>>? {
        val json = mmkv.decodeString(DATA_COURSE_LIST)
        if (json?.isEmpty() == true) return null
        return JsonUtils.fromJson<BaseResponse<List<ArticleDTO>>>(json)
    }

    fun setCacheCourseCatalogData(cid: Int, pageIndex: Int, json: String) {
        mmkv.encode("${cid}_${pageIndex}", json)
    }

    fun getCourseCatalogCacheData(cid: Int, pageIndex: Int): BaseResponse<PageDTO<ArticleDTO>>? {
        val json = mmkv.decodeString("${cid}_${pageIndex}")
        if (json?.isEmpty() == true) return null
        return JsonUtils.fromJson<BaseResponse<PageDTO<ArticleDTO>>>(json)
    }

    fun setCacheNavigationListData(json: String) {
        mmkv.encode(DATA_NAVIGATION_LIST, json)
    }

    fun getNavigationListCacheData(): BaseResponse<List<NavigationsDTO>>? {
        val json = mmkv.decodeString(DATA_NAVIGATION_LIST)
        if (json?.isEmpty() == true) return null
        return JsonUtils.fromJson<BaseResponse<List<NavigationsDTO>>>(json)

    }

    fun setCacheOfficialAccountListData(json: String){
        mmkv.encode(DATA_OFFICIAL_ACCOUNT_LIST,json)
    }

    fun getOfficialAccountCacheData():BaseResponse<List<OfficialAccountDTO>>?{
        val json = mmkv.decodeString(DATA_OFFICIAL_ACCOUNT_LIST)
        if (json?.isEmpty()==true) return null
        return JsonUtils.fromJson<BaseResponse<List<OfficialAccountDTO>>>(json)
    }
}