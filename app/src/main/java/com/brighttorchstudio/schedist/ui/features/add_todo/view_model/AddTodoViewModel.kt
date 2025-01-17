package com.brighttorchstudio.schedist.ui.features.add_todo.view_model

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
class AddTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository
) : ViewModel() {

    var todoAdded: Todo? = null

    fun addTodo(
        todo: Todo
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            localTodoRepository.addTodo(todo)
            todoAdded = todo
        }
    }

    fun undoAddTodo() {
        if (todoAdded != null) {
            viewModelScope.launch(Dispatchers.IO) {
                localTodoRepository.deleteTodo(todoAdded!!)
                todoAdded = null
            }
        }
    }

    fun showAddedTodoSnackbar(
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = scope,
            snackbarHostState = snackbarHostState,
            message = "Thêm nhiệm vụ mới thành công.",
            actionLabel = "Hoàn tác",
            onActionPerformed = {
                undoAddTodo()
            },
            onSnackbarDismiss = {
                todoAdded = null
            }
        )
    }
}