package com.zhq.jetpackcomposelearn.api

import android.graphics.pdf.PdfDocument.Page
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.BannerDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import com.zhq.jetpackcomposelearn.data.UserDTO
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
    suspend fun getQuestionsList(@Path("pageIndex") pageIndex: Int,
                                 @Query("page_size") pageSize:Int):BaseResponse<PageDTO<ArticleDTO>>

}