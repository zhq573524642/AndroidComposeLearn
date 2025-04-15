package com.zhq.jetpackcomposelearn.ui.screen.search

import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.data.SearchHotKeyDTO
import com.zhq.jetpackcomposelearn.repo.SearchRepositoryImpl
import com.zhq.jetpackcomposelearn.ui.screen.articles.BaseArticleViewModel
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
class SearchViewModelBase @Inject constructor(private val repo: SearchRepositoryImpl) :
    BaseArticleViewModel(repo) {
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

    fun searchKeyword(isClickSearch: Boolean, keyword: String) {
        if (keyword.isEmpty()) {
            "请输入搜索关键词".showToast()
            return
        }
        showLoading(isClickSearch, data = articleList)
        if (isClickSearch) {
            articleList.clear()
            currentPage = 0
        }
        launch({
            if (currentPage == 0) {
                handleRequest(repo.search(currentPage, keyword),
                    errorBlock = {
                        showError(msg = it.errorMsg)
                        true
                    }) {
                    articleList.apply { addAll(it.data.datas) }
                    if (articleList.isEmpty()) {
                        showEmpty(msg = "未搜索到相关内容")
                    } else {
                        currentPage = it.data.curPage
                        showContent(data = articleList, isLoadOver = it.data.over)
                    }
                }
            } else {
                handleRequest(repo.search(currentPage, keyword),
                    errorBlock = {
                        showLoadMoreError(data = articleList)
                        true
                    }) {
                    articleList.apply { addAll(it.data.datas) }
                    if (it.data.over) {
                        showContent(data = articleList, isLoadOver = true)
                        return@handleRequest
                    }
                    currentPage = it.data.curPage
                    showContent(data = articleList, isLoadOver = false)
                }
            }

        })
    }
}