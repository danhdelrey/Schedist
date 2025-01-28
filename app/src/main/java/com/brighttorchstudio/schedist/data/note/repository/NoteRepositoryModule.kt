package com.brighttorchstudio.schedist.data.note.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteRepositoryModule {

    @Binds
    abstract fun bindLocalNoteRepository(localNoteRepository: LocalNoteRepository): NoteRepository
}