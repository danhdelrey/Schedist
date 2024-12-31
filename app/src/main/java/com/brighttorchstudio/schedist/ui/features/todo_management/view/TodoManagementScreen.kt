package com.brighttorchstudio.schedist.ui.features.todo_management.view

import android.R.id.message
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.ui.features.todo_management.view_model.TodoManagementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoManagementScreen(
    viewModel: TodoManagementViewModel = hiltViewModel(),
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Demo") },
                actions = {
                    Button(onClick = { viewModel.addTodo() }) {
                        Text("Add Todo")
                    }
                    Button(onClick = { viewModel.deleteAll() }) {
                        Text("Delete All")
                    }
                    Button(onClick = { navController.navigate("screen1") }) {
                        Text("Screen1")
                    }
                }
            )
        }
    ) { innerPadding ->
        when(uiState){
            is TodoManagementViewModel.UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(innerPadding))
            }
            is TodoManagementViewModel.UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    itemsIndexed((uiState as TodoManagementViewModel.UiState.Success).todoList) { index, todo ->
                        Text(todo.title)
                    }
                }
            }
            is TodoManagementViewModel.UiState.Error -> {
                Text("Error: ${(uiState as TodoManagementViewModel.UiState.Error).message}", modifier = Modifier.padding(innerPadding))
            }
        }
    }
}
