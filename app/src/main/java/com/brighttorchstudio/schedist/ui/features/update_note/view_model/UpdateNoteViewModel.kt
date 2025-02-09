package com.brighttorchstudio.schedist.ui.features.update_note.view_model

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.core.helpers.UIComponentHelper
import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.data.note.repository.NoteRepository
import com.brighttorchstudio.schedist.data.note_with_tags.repository.NoteWithTagsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val localNoteRepository: NoteRepository,
    private val localNoteWithTagsRepository: NoteWithTagsRepository,
) : ViewModel() {
    var newNote: Note? = null
    var oldNote: Note? = null

    fun addNote(note: Note, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch(Dispatchers.IO) {
            localNoteRepository.addNote(note)
            if (note.tags.isNotEmpty()) {
                note.tags.map {
                    localNoteWithTagsRepository.addTagToNote(note.id, it.id)
                }
            }
            newNote = note
        }
        showNoteSnackBar(snackbarHostState, "Thêm ghi chú thành công.")
    }


    fun updateNote(newNote: Note, note: Note, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch(Dispatchers.IO) {
            localNoteRepository.updateNote(newNote)
            oldNote = note
        }

        showNoteSnackBar(snackbarHostState, "Cập nhật ghi chú thành công.")
    }

    fun showNoteSnackBar(
        snackbarHostState: SnackbarHostState,
        _message: String
    ) {
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
            snackbarHostState = snackbarHostState,
            message = _message,
            actionLabel = "Hoàn tác",
            onActionPerformed = { undoAction() },
            onSnackbarDismiss = {
                newNote = null
                oldNote = null
            }
        )

    }

    /*fun showErrorSnackBar(
        snackbarHostState: SnackbarHostState
    ){
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
            snackbarHostState = snackbarHostState,
            message = "Loi",
            actionLabel = "",
            onActionPerformed = {},
            onSnackbarDismiss = {
                newNote = null
                oldNote = null
            }
        )

    }*/

    fun undoAction() {
        viewModelScope.launch(Dispatchers.IO) {
            //undo add
            newNote?.let {
                localNoteRepository.deleteNote(it)
                newNote = null
            }

            //undo update
            oldNote?.let {
                localNoteRepository.updateNote(it)
                oldNote = null
            }
        }
    }
}