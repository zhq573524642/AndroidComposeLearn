package com.zhq.jetpackcomposelearn.ui.screen.search

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.SearchHotKeyDTO
import com.zhq.jetpackcomposelearn.repo.SearchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/9 16:33
 * Description
 */

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: SearchRepositoryImpl) :
    BaseViewModel<Unit>() {
    private val _hotKeyList = MutableStateFlow<List<SearchHotKeyDTO>>(emptyList())

    val hotKeyList: StateFlow<List<SearchHotKeyDTO>> get() = _hotKeyList

    private val _websiteList = MutableStateFlow<List<SearchHotKeyDTO>>(emptyList())

    val websiteList: StateFlow<List<SearchHotKeyDTO>> get() = _websiteList

    init {
        getSearchHotKey()
        getCommonWebsite()
    }

    fun getSearchHotKey() {
        launch({
            handleRequest(repo.getSearchHotKey()) {
                _hotKeyList.value = it.data
            }
        })
    }

    fun getCommonWebsite() {
        launch({
            handleRequest(repo.getCommonWebsite()) {
                _websiteList.value = it.data
            }
        })
    }
}