package com.zhq.jetpackcomposelearn.ui.screen.mine.todo

import com.zhq.commonlib.base.BaseViewModel
import com.zhq.commonlib.data.model.BaseResponse
import com.zhq.commonlib.utils.ToastUtils.showToast
import com.zhq.jetpackcomposelearn.data.TodoDTO
import com.zhq.jetpackcomposelearn.repo.MineRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.ArrayList
import javax.inject.Inject

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/17 15:36
 * Description
 */
@HiltViewModel
open class BaseTodoViewModel @Inject constructor(private val repo: MineRepositoryImpl) :
    BaseViewModel<List<TodoDTO>>() {

    var currentPage: Int = 1
    val todoList = ArrayList<TodoDTO>()

    private val _finishTodoDeleteEvent = MutableStateFlow<Int>(0)
    val finishTodoDeleteEvent: StateFlow<Int> get() = _finishTodoDeleteEvent

    private val _unFinishTodoDeleteEvent = MutableStateFlow<Int>(0)
    val unFinishTodoDeleteEvent: StateFlow<Int> get() = _unFinishTodoDeleteEvent


    private val _finishTodoUpdateEvent = MutableStateFlow<TodoDTO?>(null)
    val finishTodoUpdateEvent: StateFlow<TodoDTO?> get() = _finishTodoUpdateEvent

    private val _unFinishTodoUpdateEvent = MutableStateFlow<TodoDTO?>(null)
    val unFinishTodoUpdateEvent: StateFlow<TodoDTO?> get() = _unFinishTodoUpdateEvent


    fun getBaseTodoList(isRefresh: Boolean, status: Int, type: Int, priority: Int, orderby: Int) {
        showLoading(isClearContent = isRefresh, data = todoList)
        if (isRefresh) {
            currentPage = 1
            todoList.clear()
        }

        launch({
            if (currentPage == 1) {
                handleRequest(repo.getTodoList(currentPage, status, type, priority, orderby),
                    errorBlock = {
                        showError(msg = it.errorMsg)
                        true
                    }) {
                    todoList.addAll(it.data.datas)
                    if (todoList.isNotEmpty()) {
                        currentPage = it.data.curPage + 1
                        showContent(data = todoList, isLoadOver = it.data.over)
                    } else {
                        showEmpty("快来添加Todo吧~")
                    }
                }
            } else {
                handleRequest(repo.getTodoList(currentPage, status, type, priority, orderby),
                    errorBlock = {
                        showLoadMoreError(data = todoList, msg = it.errorMsg)
                        true
                    }) {
                    todoList.addAll(it.data.datas)
                    if (it.data.over) {
                        showContent(data = todoList, true)
                        return@handleRequest
                    }
                    currentPage = it.data.curPage + 1
                    showContent(data = todoList, false)
                }
            }
        })
    }

    fun createTodo(
        title: String, content: String, date: String, type: Int, priority: Int,
        onSuccess: () -> Unit
    ) {
        launch({
            handleRequest(repo.createTodo(title, content, date, type, priority)) {
                "创建成功".showToast()
                onSuccess.invoke()
            }
        })
    }

    fun deleteTodo(fromStatus: Int, id: Int) {
        launch({
            handleRequest(repo.deleteTodo(id)) {
                "删除成功".showToast()
                if (fromStatus == 1) {
                    _finishTodoDeleteEvent.value = id
                } else {
                    _unFinishTodoDeleteEvent.value = id
                }
            }
        })
    }

    fun updateTodo(
        id: Int, title: String, content: String, date: String, status: Int,
        type: Int, priority: Int
    ) {
        launch({
            handleRequest(repo.updateTodo(id, title, content, date, status, type, priority)) {
                "修改成功".showToast()
                if (status == 1) {
                    _finishTodoUpdateEvent.value = TodoDTO(
                        id = id, title = title, content = content, dateStr = date,
                        status = status,
                        type = type,
                        priority = priority
                    )
                } else {
                    _unFinishTodoUpdateEvent.value = TodoDTO(
                        id = id, title = title, content = content, dateStr = date,
                        status = status,
                        type = type,
                        priority = priority
                    )
                }
            }
        })

    }

    fun changeTodoStatus(item: TodoDTO, id: Int, toStatus: Int, onSuccess: (Int) -> Unit) {
        launch({
            handleRequest(repo.changeTodoStatus(id, toStatus)) {
                "修改成功".showToast()

                if (toStatus == 1) {
                    //未完成->已完成 未完成列表移除
                    _unFinishTodoDeleteEvent.value = id
                } else {
                    //已完成->未完成
                    _finishTodoDeleteEvent.value = id
                }
                onSuccess.invoke(toStatus)
            }
        })
    }
}