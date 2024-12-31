package com.brighttorchstudio.schedist.data.todo.repository

import com.brighttorchstudio.schedist.data.todo.model.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteTodoRepository @Inject constructor() : TodoRepository {
    override fun getTodos(): Flow<List<Todo>> {
        TODO("Not yet implemented")
    }

    override suspend fun addTodo(todo: Todo) {
        println("New todo is added on the server")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}