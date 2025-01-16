package com.brighttorchstudio.schedist.ui.features.todo_management.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.helpers.DateTimeHelper
import com.brighttorchstudio.schedist.helpers.UIComponentHelper.Companion.showSnackBar
import com.brighttorchstudio.schedist.ui.features.todo_management.view_model.TodoManagementViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoManagementScreen(
    viewModel: TodoManagementViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isSelectionMode by viewModel.isSelectionMode.collectAsStateWithLifecycle()
    val selectedTodos by viewModel.selectedTodos.collectAsStateWithLifecycle()


    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var inputTodoTitle by remember { mutableStateOf("") }
    var inputTodoDescription by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.height(300.dp),
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
                inputTodoTitle = ""
                inputTodoDescription = ""
            },
            dragHandle = {},
        ) {
            LaunchedEffect(showBottomSheet) { // Key là showBottomSheet
                if (showBottomSheet) { // Chỉ requestFocus khi BottomSheet được hiển thị
                    focusRequester.requestFocus()
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
            ) {
                Column(

                ) {
                    Text(
                        text = DateTimeHelper.formatLocalDateTime(LocalDateTime.now()),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                    )
                    TextField(
                        value = inputTodoTitle,
                        textStyle = MaterialTheme.typography.titleLarge,
                        placeholder = {
                            Text(
                                text = "Tên nhiệm vụ",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.outline
                            )
                        },
                        onValueChange = { inputTodoTitle = it },
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        maxLines = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )
                    TextField(
                        value = inputTodoDescription,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        placeholder = {
                            Text(
                                text = "Nhập mô tả cho nhiệm vụ này...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.outline
                            )
                        },
                        onValueChange = { inputTodoDescription = it },
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        maxLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    var selectedItem by remember { mutableStateOf("Không quan trọng") }
                    Column(

                    ) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            DropdownMenuItem(
                                text = { Text("Rất quan trọng") },
                                onClick = {
                                    selectedItem = "Rất quan trọng"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Quan trọng") },
                                onClick = {
                                    selectedItem = "Quan trọng"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Bình thường") },
                                onClick = {
                                    selectedItem = "Bình thường"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Không quan trọng") },
                                onClick = {
                                    selectedItem = "Không quan trọng"
                                    expanded = false
                                }
                            )
                        }
                        Button(
                            onClick = {
                                expanded = !expanded
                            },
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(selectedItem)
                        }
                    }
                }
            }
        }
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
                                viewModel.exitSelectionMode()
                            }
                        ) {
                            Text("Quay lại")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            enabled = selectedTodos.size < ((uiState as? TodoManagementViewModel.UiState.Success)?.todoList?.size
                                ?: 0),
                            onClick = {
                                viewModel.selectAllTodos(
                                    (uiState as? TodoManagementViewModel.UiState.Success)?.todoList
                                        ?: emptyList()
                                )
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
                BottomAppBar(
                    actions = {
                        TextButton(
                            onClick = {
                                viewModel.deleteSelectedTodos()
                                showSnackBar(
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    message = "Đã xóa nhiệm vụ.",
                                    actionLabel = "Hoàn tác",
                                    onActionPerformed = {
                                        viewModel.undoDeleteSelectedTodos()
                                    },
                                )
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                )
            } else {
                NavigationBar {
                    NavigationBarItem(
                        selected = true,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "home"
                            )
                        },
                        label = { Text("Home") },
                        onClick = { }
                    )
                }
            }
        },
        floatingActionButton = {
            if (!isSelectionMode) {
                FloatingActionButton(
                    onClick = {
                        showBottomSheet = true
//                        viewModel.addTodo()
//                        showSnackBar(
//                            scope = scope,
//                            snackbarHostState = snackbarHostState,
//                            message = "Thêm nhiệm vụ mới thành công.",
//                            actionLabel = "Hoàn tác",
//                            onActionPerformed = {
//                                viewModel.undoAddTodo()
//                            },
//                        )
                    },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Todo")
                }
            }
        }
    ) { innerPadding ->
        when (uiState) {
            is TodoManagementViewModel.UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(innerPadding))
            }

            is TodoManagementViewModel.UiState.Success -> {
                val todoList = (uiState as TodoManagementViewModel.UiState.Success).todoList
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    todoList.forEach { todo ->
                        TodoItem(
                            todo = todo,
                            viewModel = viewModel,
                            isSelected = todo in selectedTodos,
                            onToggleSelection = { viewModel.toggleTodoSelection(todo) },
                            onClick = {}
                        )
                    }
                }
            }

            is TodoManagementViewModel.UiState.Error -> {
                Text(
                    "Error: ${(uiState as TodoManagementViewModel.UiState.Error).message}",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}


