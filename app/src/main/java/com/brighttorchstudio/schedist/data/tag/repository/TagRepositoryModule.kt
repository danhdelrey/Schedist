package com.brighttorchstudio.schedist.data.tag.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class TagRepositoryModule {
    @Binds
    abstract fun bindLocalTagRepository(localTagRepository : LocalTagRepository) : TagRepository
}