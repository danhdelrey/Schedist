package com.brighttorchstudio.schedist.ui.features.update_note.view_model

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import com.brighttorchstudio.schedist.core.helpers.UIComponentHelper
import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.data.note.repository.LocalNoteRepository
import com.brighttorchstudio.schedist.data.note.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val localNoteRepository: NoteRepository
): ViewModel(){
    var newNote : Note? = null
    var oldNote : Note? = null

    fun addNote(note : Note, snackbarHostState: SnackbarHostState){
        var toAddedNote: Note = note
        viewModelScope.launch(Dispatchers.IO) {
            localNoteRepository.addNote(note)
            newNote = note
        }
        showNoteSnackBar(snackbarHostState, "Thêm ghi chú thành công.")
    }



    fun updateNote(newNote: Note, note: Note, snackbarHostState: SnackbarHostState){
        viewModelScope.launch(Dispatchers.IO) {
            localNoteRepository.updateNote(newNote)
            oldNote = note
        }

        showNoteSnackBar(snackbarHostState, "Cập nhật ghi chú thành công.")
    }

    fun showNoteSnackBar(
        snackbarHostState: SnackbarHostState,
        _message : String
        ){
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
            snackbarHostState = snackbarHostState,
            message = _message,
            actionLabel = "Hoàn tác",
            onActionPerformed = {undoAction()},
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

    fun undoAction(){
        viewModelScope.launch(Dispatchers.IO) {
            //undo add
            newNote?.let{
                localNoteRepository.deleteNote(it)
                newNote=null}

            //undo update
            oldNote?.let {
                localNoteRepository.updateNote(it)
                oldNote=null}
        }
    }
}