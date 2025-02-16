package com.brighttorchstudio.schedist.ui.features.manage_tag.view_model

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.core.helpers.UIComponentHelper
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

    fun addTag(
        tag: Tag
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            recentTag = tag
            localTagRepository.addTag(tag)
        }
    }

    fun deleteTag(
        tag: Tag
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            recentTag = tag
            localTagRepository.deleteTag(tag)
        }
    }

    fun updateTag(
        tag: Tag
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            recentTag = tag
            localTagRepository.addTag(tag)
        }
    }

    fun undoAddTag() {
        viewModelScope.launch(Dispatchers.IO) {
            recentTag?.let {
                localTagRepository.deleteTag(it)
                recentTag = null
            }
        }
    }

    fun undoDeleteTag() {
        viewModelScope.launch(Dispatchers.IO) {
            recentTag?.let {
                localTagRepository.addTag(it)
                recentTag = null
            }
        }
    }

    fun undoUpdateTag() {
        viewModelScope.launch(Dispatchers.IO) {
            recentTag?.let {
                localTagRepository.addTag(it)
                recentTag = null
            }
        }
    }

    fun showAddedTagSnackbar(
        snackbarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
            snackbarHostState = snackbarHostState,
            message = "Đã thêm nhãn '${recentTag?.name}'.",
            actionLabel = "Hoàn tác",
            onActionPerformed = {
                undoAddTag()
            },
            onSnackbarDismiss = {
                recentTag = null
            }
        )
    }

    fun showUpdatedTagSnackbar(
        snackbarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
            snackbarHostState = snackbarHostState,
            message = "Đã cập nhật nhãn '${recentTag?.name}'.",
            actionLabel = "Hoàn tác",
            onActionPerformed = {
                undoUpdateTag()
            },
            onSnackbarDismiss = {
                recentTag = null
            }
        )
    }

    fun showDeletedTagSnackbar(
        snackbarHostState: SnackbarHostState,
    ) {
        UIComponentHelper.showSnackBar(
            scope = viewModelScope,
            snackbarHostState = snackbarHostState,
            message = "Đã xóa nhãn '${recentTag?.name}'.",
            actionLabel = "Hoàn tác",
            onActionPerformed = {
                undoDeleteTag()
            },
            onSnackbarDismiss = {
                recentTag = null
            }
        )
    }


}