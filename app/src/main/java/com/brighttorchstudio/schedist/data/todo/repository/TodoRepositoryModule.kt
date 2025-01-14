package com.brighttorchstudio.schedist.data.todo.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
abstract class TodoRepositoryModule {

    @Binds
    abstract fun bindLocalTodoRepository(localTodoRepository: LocalTodoRepository): TodoRepository

}