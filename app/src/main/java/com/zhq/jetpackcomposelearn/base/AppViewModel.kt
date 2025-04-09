package com.zhq.jetpackcomposelearn.base
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zhq.commonlib.base.BaseAppViewModel
import com.zhq.jetpackcomposelearn.data.CollectDataDTO
import com.zhq.jetpackcomposelearn.data.UserDTO
import com.zhq.jetpackcomposelearn.data.UserInfoDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 16:38
 * Description
 */
class AppViewModel :BaseAppViewModel() {

    /** 全局用户 */
    private val _user = MutableStateFlow(UserManager.getUser())
    val user: StateFlow<UserInfoDTO?> = _user

    /** 分享添加文章 */
    private val _shareArticleEvent = MutableLiveData<Boolean>()
    val shareArticleEvent: LiveData<Boolean> = _shareArticleEvent

    /** 文章收藏 */
    private val _collectEvent = MutableLiveData<CollectDataDTO>()
    val collectEvent: LiveData<CollectDataDTO> = _collectEvent

    /** emit全局用户 */
    fun emitUser(user: UserInfoDTO?) {
        _user.value = user
    }

    /** 发送分享文章成功的消息 */
    fun emitShareArticleSuccess() {
        _shareArticleEvent.value = true
    }

    /** 发送收藏/取消收藏成功的消息 */
    fun emitCollectEvent(collectData: CollectDataDTO) {
        _collectEvent.value = collectData
    }

}