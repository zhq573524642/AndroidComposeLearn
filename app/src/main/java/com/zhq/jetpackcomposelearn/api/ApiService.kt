package com.zhq.jetpackcomposelearn.api

import android.graphics.pdf.PdfDocument.Page
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.BannerDTO
import com.zhq.jetpackcomposelearn.data.MessageDTO
import com.zhq.jetpackcomposelearn.data.NavigationsDTO
import com.zhq.jetpackcomposelearn.data.OfficialAccountDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import com.zhq.jetpackcomposelearn.data.SearchHotKeyDTO
import com.zhq.jetpackcomposelearn.data.ShareUserDTO
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
    suspend fun getUserInfo(): BaseResponse<UserInfoDTO>

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
        @Path("pageIndex") pageIndex: Int
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

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    suspend fun getSearchHotKeys(): BaseResponse<List<SearchHotKeyDTO>>

    /**
     * 常用网站
     */
    @GET("friend/json")
    suspend fun getCommonWebsite(): BaseResponse<List<SearchHotKeyDTO>>

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{id}/json")
    suspend fun handleCollectArticle(@Path("id") id: Int): BaseResponse<Unit>

    /**
     * 取消收藏文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun handleUncollectArticle(@Path("id") id: Int): BaseResponse<Unit>

    /**
     * 获取我的收藏文章列表
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getMyCollectArticle(@Path("page") pageIndex: Int): BaseResponse<PageDTO<ArticleDTO>>

    /**
     * 移除我的收藏列表的文章
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    suspend fun handleUnCollectArticleForMine(
        @Path("id") id: Int,
        @Field("originId") originId: Int
    ): BaseResponse<Unit>

    /**
     * 收藏网站列表
     */
    @GET("lg/collect/usertools/json")
    suspend fun getCollectWebsiteList(): BaseResponse<List<ArticleDTO>>

    /**
     * 删除收藏网站
     */
    @FormUrlEncoded
    @POST("lg/collect/deletetool/json")
    suspend fun deleteCollectWebsite(@Field("id") id: Int): BaseResponse<Unit>

    /**
     * 获取导航数据
     */
    @GET("navi/json")
    suspend fun getNavigationData(): BaseResponse<List<NavigationsDTO>>

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun search(
        @Path("page") pageIndex: Int,
        @Field("k") key: String
    ): BaseResponse<PageDTO<ArticleDTO>>

    /**
     * 消息-已读
     */
    @GET("message/lg/readed_list/{page}/json")
    suspend fun getReadMessage(@Path("page") pageIndex: Int): BaseResponse<PageDTO<MessageDTO>>

    /**
     * 消息-未读
     */
    @GET("message/lg/unread_list/{page}/json")
    suspend fun getUnreadMessage(@Path("page") pageIndex: Int): BaseResponse<PageDTO<MessageDTO>>

    /**
     * 未读消息数量
     */
    @GET("message/lg/count_unread/json")
    suspend fun getUnreadCount(): BaseResponse<Int>

    /**
     * 获取公众号列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun getOfficialAccountsList(): BaseResponse<List<OfficialAccountDTO>>

    /**
     * 查看某个公众号的历史数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getArticleForOfficialAccount(
        @Path("id") id: Int,
        @Path("page") pageIndex: Int
    ): BaseResponse<PageDTO<ArticleDTO>>

    /**
     * 广场列表数据
     */
    @GET("user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") pageIndex: Int): BaseResponse<PageDTO<ArticleDTO>>

    /**
     * 分享人对应列表数据
     */
    @GET("user/{id}/share_articles/{page}/json")
    suspend fun getArticleListForShareUser(@Path("page") pageIndex: Int, @Path("id") id: Int):
            BaseResponse<ShareUserDTO>
}