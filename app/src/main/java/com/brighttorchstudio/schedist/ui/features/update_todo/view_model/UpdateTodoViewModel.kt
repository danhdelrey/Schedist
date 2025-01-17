package com.brighttorchstudio.schedist.ui.features.update_todo.view_model

import androidx.lifecycle.ViewModel
import com.brighttorchstudio.schedist.data.todo.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateTodoViewModel @Inject constructor(
    private val localTodoRepository: TodoRepository
) : ViewModel() {


}