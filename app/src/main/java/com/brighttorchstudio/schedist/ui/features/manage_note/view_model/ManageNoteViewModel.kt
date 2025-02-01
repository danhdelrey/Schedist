package com.brighttorchstudio.schedist.ui.features.manage_note.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.data.note.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageNoteViewModel @Inject constructor(
    private val localNoteRepository: NoteRepository
) : ViewModel(){

    sealed class UiState{
        object Loading : UiState()
        data class Success(val notes : List<Note>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState : StateFlow<UiState> = _uiState.asStateFlow()

    private val _inSelectMode = MutableStateFlow(false)
    val inSelectMode : StateFlow<Boolean> = _inSelectMode.asStateFlow()

    private val _selectedNotesForPerformingAction = MutableStateFlow<Set<Note>>(emptySet())
    val selectedNotesForPerformingAction : StateFlow<Set<Note>>  = _selectedNotesForPerformingAction.asStateFlow()

    private val _selectedNoteForUpdating = MutableStateFlow<Note?>(null)
    val selectedNoteForUpdating : StateFlow<Note?> = _selectedNoteForUpdating.asStateFlow()

    private val _selectAllNotes = MutableStateFlow(false)
    val selectAllNotes : StateFlow<Boolean> = _selectAllNotes.asStateFlow()

    init{
        viewModelScope.launch {
            localNoteRepository.getNotes().collect{
                notes -> _uiState.value = UiState.Success(notes)
            }
        }
    }

    fun onNoteClicked(note:Note){
        if (_inSelectMode.value){
            _selectedNotesForPerformingAction.value =
                if (note in _selectedNotesForPerformingAction.value){
                    _selectedNotesForPerformingAction.value - note
                }
                else
                    _selectedNotesForPerformingAction.value + note
        }
        else{
            _selectedNoteForUpdating.value = note
        }
    }

    fun onNoteLongClicked(note:Note){
        if (!_inSelectMode.value){
            _inSelectMode.value = true
            _selectedNotesForPerformingAction.value =
                if (note in _selectedNotesForPerformingAction.value){
                    _selectedNotesForPerformingAction.value - note
                }
                else
                    _selectedNotesForPerformingAction.value + note
        }

    }

    fun exitSelectMode(){
        _inSelectMode.value = false
        _selectAllNotes.value = false
        _selectedNotesForPerformingAction.value = emptySet()
    }

    fun cancelNoteUpdate(){
        _selectedNoteForUpdating.value=null
    }

    fun onSelectAllNotesClicked(){
        _selectAllNotes.value = true
        _selectedNotesForPerformingAction.value = emptySet()
        _selectedNotesForPerformingAction.value = (_uiState.value as UiState.Success).notes.toSet()
    }

    fun onDeselectAllNotesClicked(){
        _selectAllNotes.value = false
        _selectedNotesForPerformingAction.value = emptySet()
    }

}