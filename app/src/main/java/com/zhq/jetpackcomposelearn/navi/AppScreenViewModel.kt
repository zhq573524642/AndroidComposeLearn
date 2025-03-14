package com.zhq.jetpackcomposelearn.navi

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.base.AppCacheManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/5 17:08
 * Description
 */
@HiltViewModel
class AppScreenViewModel @Inject constructor() : BaseViewModel<Unit>() {

    private val _isFirstOpen = MutableStateFlow(AppCacheManager.isFirstOpen())
    val isFirstOpen: StateFlow<Boolean> = _isFirstOpen

    fun emitFirstUse(firstOpen: Boolean) {
        _isFirstOpen.value = firstOpen
        AppCacheManager.saveFirstOpen(firstOpen)
    }
}