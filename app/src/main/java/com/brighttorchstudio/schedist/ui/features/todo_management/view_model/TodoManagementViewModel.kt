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

    fun addTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newTodo = Todo(
                    id = UUID.randomUUID().toString(),
                    title = "Mua đồ ăn",
                    description = "Mua sữa, trứng, bánh mì",
                    priority = 1,
                    dateTime = LocalDateTime.now().plusDays(1),
                    reminderEnabled = false,
                )
                localTodoRepository.addTodo(newTodo)
            }catch (e: Exception){
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localTodoRepository.deleteTodo(todo)
            }catch (e: Exception){
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun deleteAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localTodoRepository.deleteAllTodos()
            }catch (e: Exception){
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

}



