package com.brighttorchstudio.schedist.ui.features.update_tag.view_model

import androidx.lifecycle.ViewModel
import com.brighttorchstudio.schedist.data.tag.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateTagViewModel @Inject constructor(
    private val localTagRepository: TagRepository
) : ViewModel() {

}