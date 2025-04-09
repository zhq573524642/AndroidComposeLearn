package com.zhq.jetpackcomposelearn.ui.screen.projects

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.data.ArticleDTO
import com.zhq.jetpackcomposelearn.data.PageDTO
import com.zhq.jetpackcomposelearn.repo.ProjectRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.math.log

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/3 13:49
 * Description
 */
private const val TAG = "ProjectsViewModel"

@HiltViewModel
class ProjectsViewModel @Inject constructor(private val repo: ProjectRepositoryImp) :
    BaseViewModel<Unit>() {

    //Tabs
    private val _tabsState = mutableStateOf<DataResults<List<ArticleDTO>>>(DataResults.Loading())
    val tabsState: State<DataResults<List<ArticleDTO>>> = _tabsState

    private val _tabCount = mutableStateOf(0)
    val tabCount: Int = _tabCount.value

    //选中的Index
    private val _selectedIndex = mutableStateOf(0)
    val selectedIndex: Int get() = _selectedIndex.value

    //对应列表数据
    private val _pagerStates = mutableStateMapOf<Int, PagerPageState<ArticleDTO>>()

    fun getPagerState(tabId: Int): PagerPageState<ArticleDTO> {
        return _pagerStates[tabId] ?: PagerPageState()
    }

    init {
        loadTabData()
    }

    fun selectTab(index: Int) {
        _selectedIndex.value = index
        loadPagerDataIfNeeded(index)
    }

    fun refreshData(tabIndex: Int) {
        loadPagerData(tabIndex, isRefresh = true)
    }


    fun loadTabData() {
        launch({
            _tabsState.value = DataResults.Loading()
            handleRequest(repo.getProjectsType(),
                errorBlock = {
                    _tabsState.value = DataResults.Error(it.errorMsg)
                    true
                }) {
                _tabsState.value = DataResults.Success(it.data)
                _tabCount.value = it.data.size
                loadPagerData(0, isRefresh = true)
            }
        })
    }

    private fun loadPagerDataIfNeeded(tabIndex: Int) {
        val tabId = getTabId(tabIndex) ?: return
        if (!_pagerStates.containsKey(tabId)) {
            loadPagerData(tabIndex, isRefresh = true)
        }
    }

    fun loadPagerData(tabIndex: Int, isRefresh: Boolean = true) {
        val tabId = getTabId(tabIndex) ?: return
        val currentState = _pagerStates[tabId] ?: PagerPageState()
        if (currentState.isLoadingMore) return
        if (!isRefresh && currentState.pageData.curPage >= currentState.pageData.pageCount) return
        val targetPage = if (isRefresh) 1 else currentState.pageData.curPage + 1
        _pagerStates[tabId] = currentState.copy(
            isRefreshing = isRefresh,
            isLoadingMore = !isRefresh,
            error = null
        )
        launch({
            handleRequest(repo.getProjectsList(targetPage, tabId),
                errorBlock = {
                    _pagerStates[tabId] = currentState.copy(
                        error = it.errorMsg ?: "加载失败",
                        isRefreshing = false,
                        isLoadingMore = false
                    )
                    false
                }) {
                _pagerStates[tabId] = if (isRefresh) {
                    PagerPageState(
                        pageData = it.data,
                        isRefreshing = false,
                        isLoadingMore = false
                    )
                } else {
                    currentState.copy(
                        pageData = currentState.pageData.copy(
                            datas = currentState.pageData.datas + it.data.datas,
                            curPage = targetPage,
                            pageCount = it.data.pageCount
                        ),
                        isRefreshing = false,
                        isLoadingMore = false
                    )
                }

            }
        })
    }


    private fun getTabId(tabIndex: Int): Int? {
        return when (val state = _tabsState.value) {
            is DataResults.Success -> state.data.getOrNull(tabIndex)?.id
            else -> null
        }
    }
}