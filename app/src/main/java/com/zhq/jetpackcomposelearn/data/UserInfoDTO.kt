package com.zhq.jetpackcomposelearn.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 15:36
 * Description
 */
@Parcelize
data class UserInfoDTO(
    val coinInfo:CoinInfoDTO,
    val userInfo:UserDTO,
):Parcelable