package com.brighttorchstudio.schedist.ui.features.manage_tag.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.core.common.TagColor
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
            addTempTags()
            localTagRepository.getTags().collect { tags ->
                _uiState.value = UiState.Success(tags)
            }
        }
    }

    fun addTempTags() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempTag: List<Tag> = listOf(
                Tag("a1", "Công việc", TagColor.BLUE_GRAY),
                Tag("a2", "Quan trọng", TagColor.RED),
                Tag("a3", "Gia đình", TagColor.PINK),
                Tag("a4", "Học tập", TagColor.PURPLE),
                Tag("a5", "Sức khỏe", TagColor.DEEP_PURPLE),
                Tag("a6", "Tài chính", TagColor.INDIGO),
                Tag("a7", "Du lịch", TagColor.BLUE),
                Tag("a8", "Giải trí", TagColor.LIGHT_BLUE),
                Tag("a9", "Công nghệ", TagColor.CYAN),
                Tag("a10", "Thiên nhiên", TagColor.TEAL),
                Tag("a11", "Sách", TagColor.GREEN),
                Tag("a12", "Âm nhạc", TagColor.LIGHT_GREEN),
                Tag("a13", "Phim ảnh", TagColor.LIME),
                Tag("a14", "Món ăn", TagColor.YELLOW),
                Tag("a15", "Thể thao", TagColor.AMBER),
                Tag("a16", "Mua sắm", TagColor.ORANGE),
                Tag("a17", "Bạn bè", TagColor.DEEP_ORANGE),
                Tag("a18", "Nghệ thuật", TagColor.BROWN),
                Tag("a19", "Khác", TagColor.GRAY),
            )
            tempTag.map {
                localTagRepository.addTag(it)
            }
        }
    }

    fun findTagsByName(tagName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading
            try {
                val tagList = localTagRepository.getTagsByName(tagName)
                _uiState.value = UiState.Success(tagList)
            }catch (e: Exception){
                _uiState.value = UiState.Error("Loi!!")
            }

        }

    }

}