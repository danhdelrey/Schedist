package com.brighttorchstudio.schedist.ui.features.update_tag.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.data.tag.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTagViewModel @Inject constructor(
    private val localTagRepository: TagRepository
) : ViewModel(){


    fun addTag(
        tag: Tag
    ){
        viewModelScope.launch(Dispatchers.IO) {
            localTagRepository.addTag(tag)
        }
    }


}