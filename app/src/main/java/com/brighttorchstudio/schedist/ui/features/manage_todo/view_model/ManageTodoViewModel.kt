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
import kotlin.collections.minus
import kotlin.collections.plus

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


    //lưu trạng thái selection và các todo được chọn trong trạng thái này
    private val _isSelectionMode = MutableStateFlow(false)
    val isSelectionMode: StateFlow<Boolean> = _isSelectionMode.asStateFlow()
    private val _selectedTodos = MutableStateFlow(emptySet<Todo>())
    val selectedTodos: StateFlow<Set<Todo>> = _selectedTodos.asStateFlow()

    //lưu todo được chọn trong trạng thái bình thường, dùng cho việc sửa todo
    private val _selectedTodo = MutableStateFlow<Todo?>(null)
    val selectedTodo: StateFlow<Todo?> = _selectedTodo.asStateFlow()

    //Khi vừa được khởi tạo thì tiến hành lấy danh sách todo từ local database
    init {
        viewModelScope.launch {
            localTodoRepository.getTodos().collect { todos ->
                _uiState.value = UiState.Success(todos)
            }
        }
    }

    fun onTodoClicked(todo: Todo) {
        if (_isSelectionMode.value) {
            _selectedTodos.value = if (todo in _selectedTodos.value) {
                _selectedTodos.value - todo
            } else {
                _selectedTodos.value + todo
            }
        } else {
            _selectedTodo.value = todo
        }
    }

    fun onTodoLongClicked(todo: Todo) {
        if (!_isSelectionMode.value) {
            enterSelectionMode()
            _selectedTodos.value = if (todo in _selectedTodos.value) {
                _selectedTodos.value - todo
            } else {
                _selectedTodos.value + todo
            }
        }
    }

    fun isTodoSelectedInSelectionMode(todo: Todo) = todo in _selectedTodos.value

    fun cancelUpdatingTodo() {
        _selectedTodo.value = null
    }

    fun selectAllTodosInSelectionMode(allTodos: Set<Todo>) {
        _selectedTodos.value = allTodos
    }

    fun enterSelectionMode() {
        _isSelectionMode.value = true
    }

    fun exitSelectionMode() {
        _isSelectionMode.value = false
        _selectedTodos.value = emptySet()
    }


}



