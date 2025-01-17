package com.brighttorchstudio.schedist.ui.features.update_todo.view_model

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.core.helpers.UIComponentHelper
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.data.todo.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository
) : ViewModel() {
    var todoUpdated: Todo? = null

    fun updateTodo(
        todo: Todo
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            localTodoRepository.updateTodo(todo)
            todoUpdated = todo
        }
    }

    fun undoUpdateTodo() {
        if (todoUpdated != null) {
            viewModelScope.launch(Dispatchers.IO) {
                localTodoRepository.updateTodo(todoUpdated!!)
                todoUpdated = null
            }
        }
    }

    fun showUpdatedTodoSnackbar(
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = scope,
            snackbarHostState = snackbarHostState,
            message = "Cập nhật nhiệm vụ thành công.",
            actionLabel = "Hoàn tác",
            onActionPerformed = {
                undoUpdateTodo()
            },
            onSnackbarDismiss = {
                todoUpdated = null
            }
        )
    }
}

