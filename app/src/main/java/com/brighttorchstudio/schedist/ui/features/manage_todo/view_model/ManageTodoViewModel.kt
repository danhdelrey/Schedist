package com.brighttorchstudio.schedist.ui.features.manage_todo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.data.todo.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository,
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val todoList: List<Todo>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _isSelectionMode = MutableStateFlow(false)
    val isSelectionMode: StateFlow<Boolean> = _isSelectionMode.asStateFlow()

    private val _selectedTodos = MutableStateFlow<Set<Todo>>(emptySet())
    val selectedTodos: StateFlow<Set<Todo>> = _selectedTodos.asStateFlow()


    init {
        viewModelScope.launch {
            localTodoRepository.getTodos().collect { todos ->
                _uiState.value = UiState.Success(todos)
            }
        }
    }

    fun enterSelectionMode() {
        _isSelectionMode.value = true
    }

    fun exitSelectionMode() {
        _isSelectionMode.value = false
        _selectedTodos.value = emptySet()
    }

    fun toggleTodoSelection(todo: Todo) {
        _selectedTodos.value = if (todo in _selectedTodos.value) {
            _selectedTodos.value - todo
        } else {
            _selectedTodos.value + todo
        }
    }

    fun selectAllTodos(allTodos: List<Todo>) {
        _selectedTodos.value = allTodos.toSet()
    }


}



