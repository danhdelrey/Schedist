package com.brighttorchstudio.schedist.ui.features.delete_todo.view_model

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.core.helpers.UIComponentHelper
import com.brighttorchstudio.schedist.data.notification.model.Notification
import com.brighttorchstudio.schedist.data.notification.repository.NotificationRepository
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.data.todo.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository,
    private val localNotificationRepository: NotificationRepository,
) : ViewModel() {
    var todosDeleted = mutableListOf<Todo>() //lưu các todo vừa mới xóa, dùng cho việc hoàn tác

    fun deleteTodos(todos: List<Todo>) {
        viewModelScope.launch(Dispatchers.IO) {

            todos.forEach { todo ->
                localNotificationRepository.cancelNotification(todo.id)
            }

            localTodoRepository.deleteTodo(todos)
            todosDeleted.addAll(todos)
        }
    }

    fun undoDeleteTodos() {
        viewModelScope.launch(Dispatchers.IO) {

            todosDeleted.forEach { todo ->
                if (todo.reminderEnabled) {
                    localNotificationRepository.scheduleNotification(
                        Notification(
                            id = todo.id,
                            title = todo.title,
                            description = todo.description,
                            scheduledDateTime = todo.dateTime
                        )
                    )
                }
            }

            localTodoRepository.addTodo(todosDeleted)
            todosDeleted.clear()
        }
    }

    fun showDeletedTodoSnackbar(
        snackBarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
            snackbarHostState = snackBarHostState,
            message = "Xóa nhiệm vụ thành công.",
            actionLabel = "Hoàn tác",
            onActionPerformed = {
                undoDeleteTodos()
            },
            onSnackbarDismiss = {
                todosDeleted.clear()
            }
        )
    }
}