package com.brighttorchstudio.schedist.data.services.notification

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerManagerRepositoryModule {

    @Binds
    abstract fun bindTodoWorkerManagerRepository(todoWorkManagerRepository: TodoWorkerManagerRepository): WorkManagerRepository


}