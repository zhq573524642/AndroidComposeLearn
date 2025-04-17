package com.zhq.jetpackcomposelearn.ui.screen.mine.todo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhq.commonlib.base.widgets.BaseUiStateListPage
import com.zhq.commonlib.utils.DateTimeUtils
import com.zhq.jetpackcomposelearn.R
import com.zhq.jetpackcomposelearn.common.BaseScreen
import com.zhq.jetpackcomposelearn.common.CustomDatePicker
import com.zhq.jetpackcomposelearn.common.CustomRadioGroup
import com.zhq.jetpackcomposelearn.common.CustomRatingBar
import com.zhq.jetpackcomposelearn.common.HorizontalSpace
import com.zhq.jetpackcomposelearn.common.VerticalSpace
import com.zhq.jetpackcomposelearn.data.TodoDTO
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/17 10:09
 * Description
 */
@Serializable
data object TodoRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(

    viewModel: BaseTodoViewModel = hiltViewModel(),
    finishViewModel: TodoFinishedViewModel = hiltViewModel(),
    unFinishViewModel: TodoUnFinishedViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val tabs = listOf("未完成", "已完成")
    val pagerState = rememberPagerState(initialPage = 0) {
        tabs.size
    }
    val scope = rememberCoroutineScope()
    var openDialog by remember {
        mutableStateOf(false)
    }
    BaseScreen(title = "Todo", onBack = { onBackClick.invoke() }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                PrimaryTabRow(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    selectedTabIndex =
                    pagerState.currentPage,
                    containerColor = Color(0xfff5f5f5),
                    contentColor = Color.Black,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, s ->
                        Tab(selected = index == pagerState.currentPage, onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }) {
                            Text(
                                text = s,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page: Int ->
                    if (page == 0) {
                        TodoUnfinishedView(unFinishViewModel) {
                            //刷新已完成列表
                            finishViewModel.getTodoList(true, 0, 0, 4)
                        }
                    } else {
                        TodoFinishedView(finishViewModel) {
                            //刷新未完成列表
                            unFinishViewModel.getTodoList(true, 0, 0, 4)
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = {
                    openDialog = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 12.dp, bottom = 12.dp),
                containerColor = Color.Yellow,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "添加")
            }
        }
    }

    if (openDialog) {
        CreateTodoDialog(
            onDismissRequest = { openDialog = false },
            onDismissButtonClick = { openDialog = false }
        ) { title, content, date, type, priority ->
            openDialog = false
            viewModel.createTodo(title, content, date, type, priority) {
                unFinishViewModel.getTodoList(true, 0, 0, 4)
            }
        }

    }

}

@Composable
private fun TodoFinishedView(
    viewModel: TodoFinishedViewModel,
    onDataUpdate: () -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    val deleteEvent by viewModel.finishTodoDeleteEvent.collectAsState()
    val updateEvent by viewModel.finishTodoUpdateEvent.collectAsState()
    //删除
    if (deleteEvent != 0) {
        uiPageState.data = uiPageState.data?.filter {
            it.id != deleteEvent
        }
    }
    //更新
    if (updateEvent != null) {
        uiPageState.data?.forEach {
            if (it.id == updateEvent!!.id) {
                it.title = updateEvent!!.title
                it.content = updateEvent!!.content
                it.dateStr = updateEvent!!.dateStr
                it.type = updateEvent!!.type
                it.priority = updateEvent!!.priority
            }
        }
    }
    //接口请求参数  --用于筛选
    val todoType by remember {
        mutableStateOf(0)
    }
    val todoPriority by remember {
        mutableStateOf(0)
    }
    //orderby 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
    val todoOrderby by remember {
        mutableStateOf(4)
    }
    var openUpdateDialog by remember {
        mutableStateOf(false)
    }

    //用于编辑更新
    var updateId by remember {
        mutableStateOf(0)
    }
    var updateTitle by remember {
        mutableStateOf("")
    }
    var updateContent by remember {
        mutableStateOf("")
    }
    var updateDate by remember {
        mutableStateOf("")
    }
    var updateType by remember {
        mutableStateOf(1)
    }

    var updatePriority by remember {
        mutableStateOf(-1)
    }


    BaseUiStateListPage(uiPageState = uiPageState,
        contentPadding = PaddingValues(10.dp),
        itemSpace = 10.dp,
        onRefresh = {
            viewModel.getTodoList(true, todoType, todoPriority, todoOrderby)
        },
        onLoadMore = {
            viewModel.getTodoList(false, todoType, todoPriority, todoOrderby)
        }) {
        TodoItem(item = it,
            onEditTodo = { item: TodoDTO ->
                updateId = item.id
                updateTitle = item.title
                updateContent = item.content
                updateDate = item.dateStr
                updateType = item.type
                updatePriority = item.priority
                openUpdateDialog = true
            },
            onHandleTodo = { item: TodoDTO ->
                viewModel.changeTodoStatus(item, item.id, 0) {
                    onDataUpdate.invoke()
                }
            },
            onDeleteTodo = { item: TodoDTO ->
                viewModel.deleteTodo(1, item.id)
            })
    }

    if (openUpdateDialog) {
        CreateTodoDialog(
            titleParam = updateTitle,
            contentParam = updateContent,
            dateParam = updateDate,
            typeParam = updateType,
            priorityParam = updatePriority,
            onDismissRequest = { openUpdateDialog = false },
            onDismissButtonClick = { openUpdateDialog = false }
        ) { title, content, date, type, priority ->
            openUpdateDialog = false
            viewModel.updateTodo(updateId, title, content, date, 1, type, priority)
        }

    }
}

@Composable
private fun TodoUnfinishedView(
    viewModel: TodoUnFinishedViewModel,
    onDataUpdate: () -> Unit
) {
    val uiPageState by viewModel.uiPageState.collectAsState()
    val deleteEvent by viewModel.unFinishTodoDeleteEvent.collectAsState()
    val updateEvent by viewModel.unFinishTodoUpdateEvent.collectAsState()
    //删除
    if (deleteEvent != 0) {
        uiPageState.data = uiPageState.data?.filter {
            it.id != deleteEvent
        }
    }
    //更新
    if (updateEvent != null) {
        uiPageState.data?.forEach {
            if (it.id == updateEvent!!.id) {
                it.title = updateEvent!!.title
                it.content = updateEvent!!.content
                it.dateStr = updateEvent!!.dateStr
                it.type = updateEvent!!.type
                it.priority = updateEvent!!.priority
            }
        }
    }
    var openUpdateDialog by remember {
        mutableStateOf(false)
    }

    val todoType by remember {
        mutableStateOf(0)
    }
    val todoPriority by remember {
        mutableStateOf(0)
    }
    //orderby 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
    val todoOrderby by remember {
        mutableStateOf(4)
    }

    //用于编辑更新
    var updateId by remember {
        mutableStateOf(0)
    }
    var updateTitle by remember {
        mutableStateOf("")
    }
    var updateContent by remember {
        mutableStateOf("")
    }
    var updateDate by remember {
        mutableStateOf("")
    }
    var updateType by remember {
        mutableStateOf(1)
    }

    var updatePriority by remember {
        mutableStateOf(-1)
    }

    BaseUiStateListPage(uiPageState = uiPageState,
        contentPadding = PaddingValues(10.dp),
        itemSpace = 10.dp,
        onRefresh = {
            viewModel.getTodoList(true, todoType, todoPriority, todoOrderby)
        },
        onLoadMore = {
            viewModel.getTodoList(false, todoType, todoPriority, todoOrderby)
        }) {
        TodoItem(item = it,
            onEditTodo = { item: TodoDTO ->
                updateId = item.id
                updateTitle = item.title
                updateContent = item.content
                updateDate = item.dateStr
                updateType = item.type
                updatePriority = item.priority
                openUpdateDialog = true
            },
            onHandleTodo = { item: TodoDTO ->
                viewModel.changeTodoStatus(item, item.id, 1) {
                    onDataUpdate.invoke()
                }
            },
            onDeleteTodo = { item: TodoDTO ->
                viewModel.deleteTodo(item.status, item.id)
            })
    }

    if (openUpdateDialog) {
        CreateTodoDialog(
            titleParam = updateTitle,
            contentParam = updateContent,
            dateParam = updateDate,
            typeParam = updateType,
            priorityParam = updatePriority,
            onDismissRequest = { openUpdateDialog = false },
            onDismissButtonClick = { openUpdateDialog = false }
        ) { title, content, date, type, priority ->
            openUpdateDialog = false
            viewModel.updateTodo(updateId, title, content, date, 0, type, priority)
        }

    }
}

@Composable
private fun TodoItem(
    item: TodoDTO,
    onEditTodo: (TodoDTO) -> Unit,
    onHandleTodo: (TodoDTO) -> Unit,
    onDeleteTodo: (TodoDTO) -> Unit
) {
    var showEdit = remember {
        mutableStateOf(false)
    }
    Surface(
        onClick = { },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = Color.White,

        ) {
        Column(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item.dateStr, color = Color.DarkGray, fontSize = 12.sp)
                    PriorityView(priority = item.priority)
                }
                HorizontalSpace(height = 10.dp)
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                HorizontalSpace(height = 4.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.content, fontSize = 13.sp, color = Color.DarkGray,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(imageVector = Icons.Filled.Build,
                        tint = Color.Green, contentDescription = "编辑",
                        modifier = Modifier.clickable {
                            showEdit.value = !showEdit.value
                        })
                }
                HorizontalSpace(height = 8.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TodoTypeView(type = item.type)
                    Spacer(modifier = Modifier.weight(1f))
                    if (item.status == 1) {
                        Text(
                            text = "完成时间:${item.completeDateStr}",
                            fontSize = 11.sp,
                            color = Color.DarkGray
                        )
                    }
                }

            }

            //编辑按钮
            AnimatedVisibility(visible = showEdit.value) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(Color.Magenta)
                            .clickable {
                                onEditTodo.invoke(item)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "编辑"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(if (item.status == 1) Color.Yellow else Color.Green)
                            .clickable { onHandleTodo.invoke(item) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            tint = Color.White,
                            imageVector = if (item.status == 1)
                                Icons.Filled.Refresh else Icons.Filled.Check,
                            contentDescription = if (item.status == 1) "转为未完成" else "转为已完成"
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(Color.Red)
                            .clickable {
                                onDeleteTodo.invoke(item)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "删除"
                        )
                    }
                }
            }


        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CreateTodoDialog(
    titleParam: String = "",
    contentParam: String = "",
    dateParam: String = "",
    typeParam: Int = 1,
    priorityParam: Int = -1,
    onDismissRequest: () -> Unit = {},
    onDismissButtonClick: () -> Unit = {},
    onConfirmButtonClick: (
        String, String, String, Int, Int
    ) -> Unit

) {
    val title = remember {
        mutableStateOf(titleParam)
    }
    val content = remember {
        mutableStateOf(contentParam)
    }
    val date = remember {
        mutableStateOf(dateParam)
    }

    val todoType = remember {
        mutableStateOf(typeParam)
    }
    val todoPriority = remember {
        mutableStateOf(priorityParam)
    }
    var openDatePicker by remember {
        mutableStateOf(false)
    }
    AlertDialog(onDismissRequest = { onDismissRequest.invoke() },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "创建一个TODO", fontSize = 17.sp)
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                //选择优先级
                CustomRatingBar(
                    ratingCount = 5,
                    ratingSelected = priorityParam
                ) { count: Int ->
                    todoPriority.value = count
                }
                HorizontalSpace(height = 10.dp)
                //选择类型
                CustomRadioGroup(
                    title = "类型 ",
                    selectedIndex = typeParam - 1,
                    list = listOf("工作", "生活", "娱乐", "其他")
                ) { type, name ->
                    todoType.value = type
                }
                HorizontalSpace(height = 10.dp)
                OutlinedTextField(value = title.value,
                    label = {
                        Text(text = "输入标题")
                    },
                    onValueChange = { value: String ->
                        title.value = value
                    })
                HorizontalSpace(height = 10.dp)
                OutlinedTextField(value = content.value,
                    label = {
                        Text(text = "输入内容")
                    },
                    onValueChange = { value: String ->
                        content.value = value
                    })
                HorizontalSpace(height = 10.dp)
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        openDatePicker = true
                    }) {
                    Text(text = "计划完成时间:${date.value}")
                }

            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmButtonClick.invoke(
                        title.value, content.value, date.value, todoType.value, todoPriority.value
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
            ) {
                Text(text = "创建", fontSize = 14.sp, color = Color.Black)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismissButtonClick.invoke() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(text = "取消", fontSize = 14.sp, color = Color.Black)
            }
        })

    if (openDatePicker) {
        CustomDatePicker(
            onDismissRequest = { openDatePicker = false }
        ) { selectedDateMillis: Long ->
            openDatePicker = false
            date.value = DateTimeUtils.formatDate(selectedDateMillis / 1000)
        }
    }
}

@Composable
fun PriorityView(priority: Int) {
    Row {
        if (priority > 0) {
            for (i in 0 until priority) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    tint = Color.Red,
                    modifier = Modifier.size(15.dp), contentDescription = ""
                )
            }
            if ((5 - priority) > 0) {
                for (i in 0 until (5 - priority)) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = Color.LightGray,
                        modifier = Modifier.size(15.dp), contentDescription = ""
                    )
                }
            }
        } else {
            for (i in 0 until 5) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    tint = Color.LightGray,
                    modifier = Modifier.size(15.dp), contentDescription = ""
                )
            }
        }

    }
}

@Composable
fun TodoTypeView(type: Int) {
    val s = when (type) {
        1 -> "工作"
        2 -> "生活"
        3 -> "娱乐"
        else -> "其他"
    }
    val icon = when (type) {
        1 -> R.drawable.ic_todo_work
        2 -> R.drawable.ic_todo_life
        3 -> R.drawable.ic_todo_play
        else -> R.drawable.ic_todo_free
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            modifier = Modifier.size(25.dp), contentDescription = ""
        )
        VerticalSpace(width = 8.dp)
        Text(text = s, fontSize = 13.sp, color = Color.Black)
    }
}