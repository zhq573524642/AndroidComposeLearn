package com.zhq.jetpackcomposelearn.data

/**
 * 收藏实体，主要用于全局收藏事件Event
 *
 * @author LTP  2022/4/13
 */
data class CollectDataDTO(
    var id: Int = 0,
    var link: String = "",
    var collect: Boolean
)