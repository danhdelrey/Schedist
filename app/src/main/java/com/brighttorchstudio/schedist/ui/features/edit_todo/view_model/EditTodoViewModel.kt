package com.brighttorchstudio.schedist.ui.features.edit_todo.view_model

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
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository,
    private val localNotificationRepository: NotificationRepository,
) : ViewModel() {

    var todoAdded: Todo? = null //todo vừa mới được thêm, dùng cho việc hoàn tác thêm todo
    var oldTodo: Todo? = null //todo trước khi được sửa, dùng cho việc hoàn tác sửa todo

    fun addTodo(
        todo: Todo
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            if (todo.reminderEnabled) {
                localNotificationRepository.scheduleNotification(
                    notification = Notification(
                        id = todo.id,
                        title = todo.title,
                        description = todo.description,
                    ),
                    duration = 2,
                    timeUnit = TimeUnit.SECONDS
                )
            }

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

    fun updateTodo(
        todo: Todo,
        newTodo: Todo
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            localTodoRepository.updateTodo(newTodo)
            oldTodo = todo
        }
    }

    fun undoUpdateTodo() {
        if (oldTodo != null) {
            viewModelScope.launch(Dispatchers.IO) {
                localTodoRepository.updateTodo(oldTodo!!)
                oldTodo = null
            }
        }
    }

    fun showUpdatedTodoSnackbar(
        snackbarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
            snackbarHostState = snackbarHostState,
            message = "Cập nhật nhiệm vụ thành công.",
            actionLabel = "Hoàn tác",
            onActionPerformed = {
                undoUpdateTodo()
            },
            onSnackbarDismiss = {
                oldTodo = null
            }
        )
    }

    fun showAddedTodoSnackbar(
        snackbarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
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