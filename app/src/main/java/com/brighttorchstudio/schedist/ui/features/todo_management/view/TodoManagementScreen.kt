package com.brighttorchstudio.schedist.ui.features.todo_management.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.ui.features.todo_management.view_model.TodoManagementViewModel
import com.brighttorchstudio.schedist.ui.shared_view.BottomAppBarActions
import com.brighttorchstudio.schedist.ui.shared_view.BottomNavigationBar
import com.brighttorchstudio.schedist.ui.shared_view.FAB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoManagementScreen(
    viewModel: TodoManagementViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isSelectionMode by viewModel.isSelectionMode.collectAsStateWithLifecycle()
    val selectedTodos by viewModel.selectedTodos.collectAsStateWithLifecycle()


    val snackbarHostState = remember { SnackbarHostState() }



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
                BottomAppBarActions { }
            } else {
                BottomNavigationBar {
                    
                }
            }
        },
        floatingActionButton = {
            if (!isSelectionMode) {
                FAB { }
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


