package com.brighttorchstudio.schedist.data.todo.repository


import com.brighttorchstudio.schedist.data.local_database.todo.TodoDao
import com.brighttorchstudio.schedist.data.services.notification.WorkManagerRepository
import com.brighttorchstudio.schedist.data.todo.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//Là lớp chứa các logic thao tác với dữ liệu (thêm, sửa, xóa, truy xuất) cục bộ với nguồn dữ liệu là Todo
//Mọi thay đổi liên quan đến dữ liệu Todo cục bộ đều phải qua lớp này
//Implement TodoRepository interface để đảm bảo đầu vào và đầu ra rõ ràng
class LocalTodoRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val todoWorkerManagerRepository: WorkManagerRepository,
) : TodoRepository {

    override fun getTodos(): Flow<List<Todo>> = todoDao.getTodos().map { todos ->
        todos.map {
            Todo.fromEntity(it)
        }
    }

    override suspend fun addTodo(todo: Todo) {
        todoDao.addTodo(todo.toEntity())
        todoWorkerManagerRepository.scheduleReminder(
            2,
            TimeUnit.SECONDS,
            todo.id
        )
    }

    override suspend fun addTodo(todo: List<Todo>) {
        todoDao.addTodo(todo.map { it.toEntity() })
    }

    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(todo: List<Todo>) {
        todoDao.deleteTodo(todo.map { it.toEntity() })
    }

    override suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo.toEntity())
    }

    override suspend fun updateTodo(todo: List<Todo>) {
        todoDao.updateTodo(todo.map { it.toEntity() })
    }

}