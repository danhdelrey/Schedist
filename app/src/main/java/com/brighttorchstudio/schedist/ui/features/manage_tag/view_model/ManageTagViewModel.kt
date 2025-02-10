package com.brighttorchstudio.schedist.ui.features.manage_tag.view_model

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.core.common.BasicColorTagSet
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
                Tag("a1", "Công việc", BasicColorTagSet.BLUE_GRAY.color.toArgb().toLong()),
                Tag("a2", "Quan trọng", BasicColorTagSet.RED.color.toArgb().toLong()),
                Tag("a3", "Gia đình", BasicColorTagSet.PINK.color.toArgb().toLong()),
                Tag("a4", "Học tập", BasicColorTagSet.PURPLE.color.toArgb().toLong()),
                Tag("a5", "Sức khỏe", BasicColorTagSet.DEEP_PURPLE.color.toArgb().toLong()),
                Tag("a6", "Tài chính", BasicColorTagSet.INDIGO.color.toArgb().toLong()),
                Tag("a7", "Du lịch", BasicColorTagSet.BLUE.color.toArgb().toLong()),
                Tag("a8", "Giải trí", BasicColorTagSet.LIGHT_BLUE.color.toArgb().toLong()),
                Tag("a9", "Công nghệ", BasicColorTagSet.CYAN.color.toArgb().toLong()),
                Tag("a10", "Thiên nhiên", BasicColorTagSet.TEAL.color.toArgb().toLong()),
                Tag("a11", "Sách", BasicColorTagSet.GREEN.color.toArgb().toLong()),
                Tag("a12", "Âm nhạc", BasicColorTagSet.LIGHT_GREEN.color.toArgb().toLong()),
                Tag("a13", "Phim ảnh", BasicColorTagSet.LIME.color.toArgb().toLong()),
                Tag("a14", "Món ăn", BasicColorTagSet.YELLOW.color.toArgb().toLong()),
                Tag("a15", "Thể thao", BasicColorTagSet.AMBER.color.toArgb().toLong()),
                Tag("a16", "Mua sắm", BasicColorTagSet.ORANGE.color.toArgb().toLong()),
                Tag("a17", "Bạn bè", BasicColorTagSet.DEEP_ORANGE.color.toArgb().toLong()),
                Tag("a18", "Nghệ thuật", BasicColorTagSet.BROWN.color.toArgb().toLong()),
                Tag("a19", "Khác", BasicColorTagSet.GRAY.color.toArgb().toLong()),
            )
            tempTag.map {
                localTagRepository.addTag(it)
            }
        }
    }

    suspend fun findTagsByName(name: String) {
        

    }

}