package com.brighttorchstudio.schedist.ui.features.notify.view_model

import androidx.lifecycle.ViewModel
import com.brighttorchstudio.schedist.core.common.NotificationPermissionState
import com.brighttorchstudio.schedist.data.notification.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val localNotificationRepository: NotificationRepository
) : ViewModel() {

    sealed class PermissionState {
        object GrantedAndEnabled : PermissionState()
        object GrantedButDisabled : PermissionState()
        object Denied : PermissionState()
    }

    private val _permissionState =
        MutableStateFlow<PermissionState>(PermissionState.GrantedAndEnabled)
    val permissionState: StateFlow<PermissionState> = _permissionState.asStateFlow()

    init {
        checkPermissionState()
    }


    fun checkPermissionState() {
        when (localNotificationRepository.checkNotificationPermissionState()) {
            NotificationPermissionState.GRANTED_AND_ENABLED -> {
                _permissionState.value = PermissionState.GrantedAndEnabled
            }

            NotificationPermissionState.GRANTED_BUT_DISABLED -> {
                _permissionState.value = PermissionState.GrantedButDisabled
            }

            NotificationPermissionState.DENIED -> {
                _permissionState.value = PermissionState.Denied
            }
        }
    }


}