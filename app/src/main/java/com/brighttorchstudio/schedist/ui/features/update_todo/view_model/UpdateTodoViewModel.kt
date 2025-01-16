package com.brighttorchstudio.schedist.ui.features.update_todo.view_model

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.data.todo.repository.TodoRepository
import com.brighttorchstudio.schedist.helpers.UIComponentHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UpdateTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository
) : ViewModel() {

    var todosAdded = mutableListOf<Todo>()

    fun addTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            val newTodo = Todo(
                id = UUID.randomUUID().toString(),
                title = "Đem đồ ăn cho chồng đáng yêu ❤️",
                description = "Ở tòa nhà abc, hm 45, nhớ hỏi mật khẩu két sắt",
                priority = (1..4).random(),
                dateTime = LocalDateTime.now().plusDays((1..10).random().toLong()),
                reminderEnabled = false,
            )
            localTodoRepository.addTodo(newTodo)
            todosAdded.add(newTodo)
        }
    }

    fun undoAddTodo() {
        if (todosAdded.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                localTodoRepository.deleteTodo(todosAdded)
                todosAdded.clear()
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
        )
    }
}