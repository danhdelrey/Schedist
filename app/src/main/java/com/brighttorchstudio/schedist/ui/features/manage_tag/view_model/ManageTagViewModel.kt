package com.brighttorchstudio.schedist.ui.features.manage_tag.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.data.tag.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageTagViewModel @Inject constructor(
    private val localTagRepository: TagRepository
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val tagList: List<Tag>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            localTagRepository.getTags().collect { tags ->
                _uiState.value = UiState.Success(tags)
            }
        }
    }


    fun findTagsByName(tagName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading
            try {
                val tagList = localTagRepository.getTagsByName(tagName)
                _uiState.value = UiState.Success(tagList)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Loi!!")
            }

        }

    }

}