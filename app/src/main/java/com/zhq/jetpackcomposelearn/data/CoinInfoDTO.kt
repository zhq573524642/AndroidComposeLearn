package com.zhq.jetpackcomposelearn.data

import android.os.Parcelable
import com.zhq.commonlib.ext.ProvideItemKeys
import kotlinx.parcelize.Parcelize

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 15:11
 * Description
 */
@Parcelize
data class CoinInfoDTO(
    val coinCount: String="0",
    val level: String="1",
    val nickname:String="",
    val rank:Int=-1,
    val userId:String="",
    val username:String=""
):ProvideItemKeys,Parcelable {
    override fun provideKey(): Int {
        return userId.toInt()
    }
}
