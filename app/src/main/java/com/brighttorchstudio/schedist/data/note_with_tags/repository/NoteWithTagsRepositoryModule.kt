package com.brighttorchstudio.schedist.data.note_with_tags.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteWithTagsRepositoryModule {

    @Binds
    abstract fun bindNoteWithTagsRepository(localNoteWithTagsRepository: LocalNoteWithTagsRepository): NoteWithTagsRepository
}