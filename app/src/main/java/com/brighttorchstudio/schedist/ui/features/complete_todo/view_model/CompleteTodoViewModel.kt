package com.brighttorchstudio.schedist.ui.features.complete_todo.view_model

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
class CompleteTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository,
    private val localNotificationRepository: NotificationRepository,
) : ViewModel() {

    var todoCompleted: Todo? = null //todo mới vừa hoàn thành xong, được dùng cho việc hoàn tác

    fun completeTodo(
        todo: Todo
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            localNotificationRepository.cancelNotification(todo.id)
            localTodoRepository.deleteTodo(todo)
            todoCompleted = todo
        }
    }

    fun undoCompleteTodo() {
        if (todoCompleted != null) {
            viewModelScope.launch(Dispatchers.IO) {

                if (todoCompleted!!.reminderEnabled) {
                    localNotificationRepository.scheduleNotification(
                        Notification(
                            id = todoCompleted!!.id,
                            title = todoCompleted!!.title,
                            description = todoCompleted!!.description,
                            scheduledDateTime = todoCompleted!!.dateTime
                        )
                    )
                }

                localTodoRepository.addTodo(todoCompleted!!)
                todoCompleted = null
            }
        }
    }

    fun showCompletedTodoSnackbar(
        snackbarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
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