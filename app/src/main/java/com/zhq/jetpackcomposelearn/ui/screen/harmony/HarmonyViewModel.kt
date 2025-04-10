package com.zhq.jetpackcomposelearn.ui.screen.harmony

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.repo.HarmonyRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.harmony.model.HarmonyDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/3/14 16:21
 * Description
 */
@HiltViewModel
class HarmonyViewModel @Inject constructor(private val repo: HarmonyRepositoryImpl) :
    BaseViewModel<HarmonyDTO>() {

    private val _harmonyData = MutableStateFlow<HarmonyDTO?>(null)
    val harmonyData: StateFlow<HarmonyDTO?> get() = _harmonyData

    fun getHarmonyData() {
        launch({
            handleRequest(repo.getHarmonyData()) {
                _harmonyData.value = it.data
            }
        })
    }

}