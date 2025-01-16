package com.brighttorchstudio.schedist.ui.features.delete_todo.view_model

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
class DeleteTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository
) : ViewModel() {
    var todosDeleted = mutableListOf<Todo>()

    fun deleteTodos(todos: List<Todo>) {
        viewModelScope.launch(Dispatchers.IO) {
            localTodoRepository.deleteTodo(todos)
            todosDeleted.addAll(todos)
        }
    }

    fun undoDeleteTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            localTodoRepository.addTodo(todosDeleted)
            todosDeleted.clear()
        }
    }

    fun showDeletedTodoSnackbar(
        scope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = scope,
            snackbarHostState = snackBarHostState,
            message = "Thêm nhiệm vụ mới thành công.",
            actionLabel = "Hoàn tác",
            onActionPerformed = {
                undoDeleteTodos()
            },
        )
    }
}