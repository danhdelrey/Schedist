package com.brighttorchstudio.schedist.ui.features.manage_tag.view_model

import androidx.lifecycle.ViewModel
import com.brighttorchstudio.schedist.data.tag.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageTagViewModel @Inject constructor(
    localTagRepository: TagRepository
): ViewModel(){

}