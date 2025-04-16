package com.zhq.jetpackcomposelearn.ui.screen.mine.setting

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.data.GoogleMavenDTO
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/16 16:41
 * Description
 */
@HiltViewModel
class GoogleMavenPackageViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseViewModel<List<GoogleMavenDTO>>() {

    private val _packageNameList = MutableStateFlow<List<String>>(emptyList())
    val packageNameList: StateFlow<List<String>> get() = _packageNameList
    val artifactList = ArrayList<GoogleMavenDTO>()

    init {
        getPackageNameList()
    }

    fun getPackageNameList() {
        launch({
            handleRequest(repo.getGoogleMavenPackageName()) {
                _packageNameList.value = it.data
            }
        })
    }

    fun queryPackageName(key: String) {
        artifactList.clear()
        showLoading(true, data = artifactList)
        launch({
            handleRequest(repo.queryGoogleMavenPackageName(key),
                errorBlock = {
                    showError(msg = it.errorMsg)
                    true
                }) {
                artifactList.addAll(it.data)
                if (artifactList.isEmpty()) {
                    showEmpty()
                    return@handleRequest
                }
                showContent(data = artifactList, true)
            }
        })
    }
}