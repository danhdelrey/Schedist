package com.brighttorchstudio.schedist.ui.features.complete_todo.view_model

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
class CompleteTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository
) : ViewModel() {

    var todoCompleted: Todo? = null

    fun completeTodo(
        todo: Todo
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            localTodoRepository.deleteTodo(todo)
            todoCompleted = todo
        }
    }

    fun undoCompleteTodo() {
        if (todoCompleted != null) {
            viewModelScope.launch(Dispatchers.IO) {
                localTodoRepository.addTodo(todoCompleted!!)
                todoCompleted = null
            }
        }
    }

    fun showCompletedTodoSnackbar(
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = scope,
            snackbarHostState = snackbarHostState,
            message = "Hoàn thành nhiệm vụ.",
            actionLabel = "Hoàn tác",
            onActionPerformed = {
                undoCompleteTodo()
            },
            onSnackbarDismiss = {
                todoCompleted = null
            }
        )
    }
}