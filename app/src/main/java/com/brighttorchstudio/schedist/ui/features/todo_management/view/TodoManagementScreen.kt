package com.brighttorchstudio.schedist.ui.features.todo_management.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.ui.features.todo_management.view_model.TodoManagementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoManagementScreen(
    viewModel: TodoManagementViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isSelectingTodos by viewModel.isSelectingTodos.collectAsStateWithLifecycle()
    val isSelectingAllTodos by viewModel.isSelectingAllTodos.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            if (isSelectingTodos) {
                TopAppBar(
                    title = { },
                    actions = {
                        TextButton(
                            onClick = {
                                viewModel.cancelSelectingTodos()
                            }
                        ) {
                            Text("Quay lại")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            enabled = !isSelectingAllTodos,
                            onClick = {
                                viewModel.startSelectingAllTodos()
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
            if (isSelectingTodos) {
                BottomAppBar(
                    actions = {
                        TextButton(
                            onClick = {
                                viewModel.deleteSelectedTodos()
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
            if (!isSelectingTodos) {
                FloatingActionButton(
                    onClick = { viewModel.addTodo() },
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
