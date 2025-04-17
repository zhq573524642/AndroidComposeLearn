package com.zhq.jetpackcomposelearn.ui.screen.mine.todo

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.jetpackcomposelearn.data.TodoDTO
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/17 10:21
 * Description
 */
@HiltViewModel
class TodoUnFinishedViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseTodoViewModel(repo) {

    fun getTodoList(isRefresh: Boolean, type: Int, priority: Int, orderby: Int) {
        getBaseTodoList(isRefresh,0, type, priority, orderby)
    }
}