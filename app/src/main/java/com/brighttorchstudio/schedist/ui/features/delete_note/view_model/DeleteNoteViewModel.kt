package com.brighttorchstudio.schedist.ui.features.delete_note.view_model

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.core.helpers.UIComponentHelper
import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.data.note.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteNoteViewModel @Inject constructor(
    private val localNoteRepository: NoteRepository
) : ViewModel(){

    var deletedNotes : List<Note>? = emptyList()

    fun deleteNotes(noteList: List<Note>, snackbarHostState: SnackbarHostState){
        if (noteList.isNotEmpty()){
            viewModelScope.launch(Dispatchers.IO) {
                localNoteRepository.deleteNotes(noteList)
                deletedNotes = noteList
            }
            showSuccessDeleteNoteSnackBar(snackbarHostState)
        }
    }

    fun deleteAllNotes(noteList: List<Note>, snackbarHostState: SnackbarHostState){
        viewModelScope.launch(Dispatchers.IO) {
            localNoteRepository.deleteAllNotes()
            deletedNotes = noteList
        }
        showSuccessDeleteNoteSnackBar(snackbarHostState)
    }


    fun undoDeleteNotes(){
        if (deletedNotes!!.isNotEmpty())
            viewModelScope.launch(Dispatchers.IO) {
                localNoteRepository.addNotes(deletedNotes!!)
                deletedNotes = emptyList()
            }
    }

    fun showSuccessDeleteNoteSnackBar(
        snackbarHostState: SnackbarHostState){
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
            snackbarHostState = snackbarHostState,
            message = "Xóa ghi chú thành công",
            actionLabel = "Hoàn tác",
            onActionPerformed = { undoDeleteNotes() },
            onSnackbarDismiss = {
                deletedNotes = emptyList()
            }
        )
    }

}