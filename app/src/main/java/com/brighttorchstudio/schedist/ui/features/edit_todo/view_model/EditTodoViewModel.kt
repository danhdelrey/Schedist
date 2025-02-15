package com.brighttorchstudio.schedist.ui.features.edit_todo.view_model

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.core.helpers.UIComponentHelper
import com.brighttorchstudio.schedist.data.notification.model.Notification
import com.brighttorchstudio.schedist.data.notification.repository.NotificationRepository
import com.brighttorchstudio.schedist.data.todo.model.Subtask
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.data.todo.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository,
    private val localNotificationRepository: NotificationRepository,
) : ViewModel() {

    var todoAdded: Todo? = null //todo vừa mới được thêm, dùng cho việc hoàn tác thêm todo
    var oldTodo: Todo? = null //todo trước khi được sửa, dùng cho việc hoàn tác sửa todo

    private val _subtaskList = MutableStateFlow(emptyList<Subtask>())
    val subtaskList : StateFlow<List<Subtask>> = _subtaskList.asStateFlow()

    private val _showAddSubtaskTextField = MutableStateFlow(false)
    val showAddSubtaskTextField : StateFlow<Boolean> = _showAddSubtaskTextField.asStateFlow()

    fun addTodo(
        todo: Todo
    ) {
        Log.d("myTag", todo.toString());
        viewModelScope.launch(Dispatchers.IO) {

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
            todo.subtasks = _subtaskList.value
            localTodoRepository.addTodo(todo)
            todoAdded = todo
            onCancelCLicked()
        }
    }

    fun undoAddTodo() {
        if (todoAdded != null) {
            viewModelScope.launch(Dispatchers.IO) {
                localNotificationRepository.cancelNotification(todoAdded!!.id)
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

            if (newTodo.reminderEnabled) {
                localNotificationRepository.scheduleNotification(
                    Notification(
                        id = newTodo.id,
                        title = newTodo.title,
                        description = newTodo.description,
                        scheduledDateTime = newTodo.dateTime
                    )
                )
            }

            newTodo.subtasks = _subtaskList.value
            localTodoRepository.updateTodo(newTodo)
            oldTodo = todo
            onCancelCLicked()
        }
    }

    fun undoUpdateTodo() {
        if (oldTodo != null) {
            viewModelScope.launch(Dispatchers.IO) {

                if (oldTodo!!.reminderEnabled) {
                    localNotificationRepository.scheduleNotification(
                        Notification(
                            id = oldTodo!!.id,
                            title = oldTodo!!.title,
                            description = oldTodo!!.description,
                            scheduledDateTime = oldTodo!!.dateTime
                        )
                    )
                }
                Log.d("myTag", oldTodo.toString())
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

    fun setSubtaskList(subtask: List<Subtask>){
        _subtaskList.value = subtask.map { it.copy() }

    }

    fun onCancelCLicked(){
        _subtaskList.value = emptyList()
        _showAddSubtaskTextField.value = false
    }

    /*fun onAddNewSubtaskClicked(){
        if (_subtaskList.value.isEmpty() || _subtaskList.value.last().name.isNotEmpty()){
            _subtaskList.value += Subtask(name="", complete = false)
            _showAddSubtaskTextField.value = true}
    }*/

    fun addNewSubtask(subtask : Subtask){
        _subtaskList.value+= subtask
    }

    fun removeSubtask(oldSubtask: Subtask){
            _subtaskList.value-=oldSubtask

    }

    fun updateSubtaskName(newSubtaskName : String, index : Int){
            _subtaskList.value[index].name = newSubtaskName

    }

    fun updateSubtaskState(newState : Boolean, index: Int){
        Log.d("SubtaskTestingBefore",_subtaskList.value[index].toString())
        _subtaskList.value[index].complete = newState

        Log.d("SubtaskTestingAfter",_subtaskList.value[index].toString())
    }
}