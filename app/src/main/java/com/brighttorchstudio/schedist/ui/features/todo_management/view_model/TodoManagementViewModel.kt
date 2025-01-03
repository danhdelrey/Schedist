package com.brighttorchstudio.schedist.ui.features.todo_management.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.data.todo.repository.LocalTodoRepository
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.data.todo.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TodoManagementViewModel @Inject constructor(
   @Named("local_todo_repository") private val localRepository: TodoRepository,
    @Named("remote_todo_repository") private val remoteRepository: TodoRepository,
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
                localRepository.getTodos().collect { todos ->
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
                    title = "New Todo"
                )
                localRepository.addTodo(newTodo)
                remoteRepository.addTodo(newTodo) //just for testing
            }catch (e: Exception){
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                localRepository.deleteAll()
            }catch (e: Exception){
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

}



