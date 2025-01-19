package com.brighttorchstudio.schedist.ui.features.manage_todo.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.core.helpers.UIComponentHelper
import com.brighttorchstudio.schedist.core.navigation.BottomNavigationBar
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.ui.features.delete_todo.view.DeleteTodoButton
import com.brighttorchstudio.schedist.ui.features.edit_todo.view.EditTodoBottomSheet
import com.brighttorchstudio.schedist.ui.features.edit_todo.view.FABAddTodo
import com.brighttorchstudio.schedist.ui.features.manage_todo.view_model.ManageTodoViewModel
import com.brighttorchstudio.schedist.ui.shared_view.BottomActionBar

//Screen quản lý danh sách todo
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTodoScreen(
    viewModel: ManageTodoViewModel = hiltViewModel(),
    navController: NavHostController
) {
    //trạng thái danh sách todo được lấy từ viewmodel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    //cần thiết cho hiển thị snackbar
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    //lưu trạng thái selection và các todo được chọn trong trạng thái này
    var isSelectionMode by remember { mutableStateOf(false) }
    var selectedTodos by remember { mutableStateOf(emptySet<Todo>()) }

    //lưu todo được chọn trong trạng thái bình thường, dùng cho việc sửa todo
    var selectedTodo by remember { mutableStateOf<Todo?>(null) }
    if (selectedTodo != null) {
        EditTodoBottomSheet(
            todo = selectedTodo,
            onDismiss = {
                selectedTodo = null
            },
            scope = scope,
            snackbarHostState = snackbarHostState
        )
    }


    //Giao diện chính
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            //nếu trạng thái hiện tại là selection thì thay đổi topappbar thành "quay lại......chọn tất cả"
            if (isSelectionMode) {
                TopAppBar(
                    title = { },
                    actions = {
                        TextButton(
                            onClick = {
                                isSelectionMode = false
                                selectedTodos = emptySet()
                            }
                        ) {
                            Text("Quay lại")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            enabled = selectedTodos.size < ((uiState as? ManageTodoViewModel.UiState.Success)?.todoList?.size
                                ?: 0),
                            onClick = {
                                selectedTodos =
                                    (uiState as? ManageTodoViewModel.UiState.Success)?.todoList?.toSet()
                                        ?: emptySet()

                            }
                        ) {
                            Text("Chọn tất cả")
                        }
                    }
                )
            } else {
                //topappbar ở trạng thái bình thường
                TopAppBar(
                    title = {
                        Text("Danh sách nhiệm vụ")
                    },
                    navigationIcon = {
                        IconButton(onClick = { 
                            //mở side drawer
                         }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "drawer"
                            )
                        }
                    }

                )
            }
        },
        bottomBar = {
            //khi ở trạng thái selection thì bottomappbar sẽ chứa các hành động
            if (isSelectionMode) {
                //tự custom BottomActionBar, hiện tại chỉ có 1 action là xóa
                BottomActionBar(
                    action1 = {
                        DeleteTodoButton(
                            scope = scope,
                            snackBarHostState = snackbarHostState,
                            todoList = selectedTodos.toList(),
                            onDeletedTodo = {
                                isSelectionMode = false
                                selectedTodos = emptySet()
                            }
                        )
                    }
                )
            } else {
                //ở trạng thái bình thường thì nó sẽ chứa các điều hướng
                BottomNavigationBar(navController)
            }
        },
        floatingActionButton = {
            //ẩn FAB nếu đang trong trạng thái selection
            if (!isSelectionMode) {
                FABAddTodo(
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    ) { innerPadding ->
        when (uiState) {
            is ManageTodoViewModel.UiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CircularProgressIndicator()
                }
            }

            is ManageTodoViewModel.UiState.Success -> {
                val todoList = (uiState as ManageTodoViewModel.UiState.Success).todoList
                if (todoList.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        todoList.forEach { todo ->
                            TodoItem(
                                todo = todo,
                                isSelected = todo in selectedTodos,
                                onToggleSelection = {
                                    selectedTodos = if (todo in selectedTodos) {
                                        selectedTodos - todo
                                    } else {
                                        selectedTodos + todo
                                    }
                                },
                                onClick = {
                                    if (isSelectionMode) {
                                        //phải tạo một Set mới dựa trên Set cũ để jetpack compose nhận biết được thay đổi mà recompose lại giao diện
                                        selectedTodos = if (todo in selectedTodos) {
                                            selectedTodos - todo
                                        } else {
                                            selectedTodos + todo
                                        }
                                    } else {
                                        selectedTodo = todo
                                    }
                                },
                                isSelectionMode = isSelectionMode,
                                onLongClick = {
                                    //khi ấn và giữ vào một todo thì sẽ bật chế độ selection
                                    isSelectionMode = true
                                },
                                scope = scope,
                                snackBarHostState = snackbarHostState
                            )
                        }
                    }
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Text(
                            text = "Chạm vào biểu tượng + để thêm nhiệm vụ mới.",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.outline
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(200.dp)
                        )
                    }
                }
            }

            is ManageTodoViewModel.UiState.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CircularProgressIndicator()
                }
                UIComponentHelper.showSnackBar(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    message = "Có lỗi xảy ra khi tải nhiệm vụ!",
                    actionLabel = "Đóng",
                    onActionPerformed = {},
                    onSnackbarDismiss = {}
                )
            }
        }
    }
}


