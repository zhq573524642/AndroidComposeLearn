package com.zhq.jetpackcomposelearn.api

import android.graphics.pdf.PdfDocument.Page
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.BannerDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import com.zhq.jetpackcomposelearn.data.UserDTO
import com.zhq.jetpackcomposelearn.data.UserInfoDTO
import com.zhq.jetpackcomposelearn.ui.screen.harmony.model.HarmonyDTO
import com.zhq.jetpackcomposelearn.ui.screen.systems.model.SystemsDTO
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/6 16:04
 * Description
 */
interface ApiService {
    /** 登录 */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") pwd: String
    ): BaseResponse<UserDTO>

    /** 注册 */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") pwd: String,
        @Field("repassword") pwdSure: String
    ): BaseResponse<Any?>

    /**
     * 获取用户信息
     */
    @GET("user/lg/userinfo/json")
    suspend fun getUserInfo():BaseResponse<UserInfoDTO>

    /**
     * 获取Banner
     */
    @GET("banner/json")
    suspend fun getBannerData(): BaseResponse<List<BannerDTO>>

    /**
     * 获取首页置顶文章
     */
    @GET("article/top/json")
    suspend fun getArticleTopList(): BaseResponse<List<ArticleDTO>>

    /**
     * 获取首页文章列表
     */
    @GET("article/list/{pageIndex}/json")
    suspend fun getHomeArticles(
        @Path("pageIndex") pageIndex: Int,
        @Query("page_size") page_size: Int
    ): BaseResponse<PageDTO<ArticleDTO>>

    /**
     * 问答
     */
    @GET("wenda/list/{pageIndex}/json")
    suspend fun getQuestionsList(
        @Path("pageIndex") pageIndex: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<PageDTO<ArticleDTO>>

    /**
     * 鸿蒙专栏
     */
    @GET("harmony/index/json")
    suspend fun getHarmonyData(): BaseResponse<HarmonyDTO>

    /**
     * 获取项目分类
     */
    @GET("project/tree/json")
    suspend fun getProjectsType(): BaseResponse<List<ArticleDTO>>

    /**
     * 获取项目列表
     */
    @GET("project/list/{page}/json")//page 1开始
    suspend fun getProjectsList(
        @Path("page") pageIndex: Int,
        @Query("cid") cid: Int
    ): BaseResponse<PageDTO<ArticleDTO>>

    /**
     * 获取体系
     */
    @GET("tree/json")
    suspend fun getSystemsList(): BaseResponse<List<SystemsDTO>>

    /**
     * 体系下的文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getSystemChildList(
        @Path("page") pageIndex: Int,
        @Query("cid") cid: Int
    ): BaseResponse<PageDTO<ArticleDTO>>

    /**
     * 教程列表
     */
    @GET("chapter/547/sublist/json")
    suspend fun getCourseList(): BaseResponse<List<ArticleDTO>>

    /**
     * 教程目录
     */
    @GET("article/list/{page}/json")
    suspend fun getCourseCatalogList(
        @Path("page") pageIndex: Int,
        @Query("cid") cid: Int,
        @Query("order_type") order_type: Int
    ):
            BaseResponse<PageDTO<ArticleDTO>>
}