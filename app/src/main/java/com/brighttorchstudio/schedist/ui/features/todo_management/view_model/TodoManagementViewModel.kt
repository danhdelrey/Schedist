package com.brighttorchstudio.schedist.ui.features.todo_management.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.data.todo.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TodoManagementViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository,
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val todoList: List<Todo>) : UiState()
        data class Error(val message: String) : UiState()
    }


    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _isSelectingTodos = MutableStateFlow<Boolean>(false)
    val isSelectingTodos: StateFlow<Boolean> = _isSelectingTodos.asStateFlow()
    var _selectedTodos = mutableListOf<Todo>()

    init {
        viewModelScope.launch {
            try {
                localTodoRepository.getTodos().collect { todos ->
                    _uiState.value = UiState.Success(todos)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun toggleSelectingTodos() {
        _isSelectingTodos.value = !_isSelectingTodos.value
    }

    fun selectTodos(todo: Todo) {
        _selectedTodos.add(todo)
    }

    fun deleteSelectedTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localTodoRepository.deleteTodo(_selectedTodos)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun addTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newTodo = Todo(
                    id = UUID.randomUUID().toString(),
                    title = "Đem đồ ăn cho chồng đáng yêu ❤️",
                    description = "Ở tòa nhà abc, hm 45, nhớ hỏi mật khẩu két sắt",
                    priority = (1..4).random(),
                    dateTime = LocalDateTime.now().plusDays((1..10).random().toLong()),
                    reminderEnabled = false,
                )
                localTodoRepository.addTodo(newTodo)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localTodoRepository.deleteTodo(todo)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun deleteAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localTodoRepository.deleteAllTodos()
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

}



