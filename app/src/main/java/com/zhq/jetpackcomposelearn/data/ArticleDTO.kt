package com.zhq.jetpackcomposelearn.data

import com.zhq.commonlib.ext.ProvideItemKeys


data class ArticleDTO(
    var apkLink: String,
    var audit: Int,
    var author: String,
    var canEdit: Boolean,
    var chapterId: Int,
    var chapterName: String,
    var collect: Boolean,
    var courseId: Int,
    var desc: String,
    var descMd: String,
    var envelopePic: String,
    var fresh: Boolean,
    var host: String,
    var id: Int,
    var link: String,
    var niceDate: String,
    var niceShareDate: String,
    var origin: String,
    var prefix: String,
    var projectLink: String,
    var publishTime: Long,
    var realSuperChapterId: Int,
    var selfVisible: Int,
    var shareDate: Long,
    var shareUser: String,
    var superChapterId: Int,
    var superChapterName: String,
    var tags: List<TagDTO>,
    var title: String,
    var type: Int,
    var userId: Int,
    var originId: Int,
    var visible: Int,
    var zan: Int,
    var name: String,
    var children: List<ArticleDTO>,
    var cover: String
) : ProvideItemKeys {
    override fun provideKey(): Int {
        return id
    }
}


data class TagDTO(
    val name: String,
    val url: String
)

