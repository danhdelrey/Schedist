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

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTodoScreen(
    viewModel: ManageTodoViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var isSelectionMode by remember { mutableStateOf(false) }
    var selectedTodos by remember { mutableStateOf(emptySet<Todo>()) }

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


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
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
                TopAppBar(
                    title = {
                        Text("Danh sách nhiệm vụ")
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* do something */ }) {
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
            if (isSelectionMode) {
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
                BottomNavigationBar(navController)
            }
        },
        floatingActionButton = {
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
                                    isSelectionMode = true
                                }
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


