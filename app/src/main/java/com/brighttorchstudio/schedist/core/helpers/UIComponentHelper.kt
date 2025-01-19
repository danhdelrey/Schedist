package com.brighttorchstudio.schedist.core.helpers

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//Lớp này chứa các phương thức tĩnh liên quan đến việc thao tác với các thành phần UI
class UIComponentHelper {

    //companion object chứa các phương thức tĩnh
    companion object {

        //phương thức hiển thị một snackbar
        fun showSnackBar(
            scope: CoroutineScope, //cần thiết để snackbar được hiển thị trong một Scaffold
            snackbarHostState: SnackbarHostState, //cần thiết để snackbar được hiển thị trong một Scaffold
            message: String, //thông điệp của snackbar
            actionLabel: String, //Tên của action
            onActionPerformed: () -> Unit, //Thực hiện khi nhấn vào action
            onSnackbarDismiss: () -> Unit, //Thực hiện khi snackbar biến mất
        ) {
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss() //Xóa tất cả snackbar trước đó, chỉ cho tồn tại 1 lần 1 snackbar, tránh trường hợp snackbar xuất hiện liên hồi

                //hiển thị snackbar
                val result = snackbarHostState
                    .showSnackbar(
                        message = message,
                        actionLabel = actionLabel,
                        duration = SnackbarDuration.Short
                    )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        onActionPerformed()
                    }

                    SnackbarResult.Dismissed -> {
                        onSnackbarDismiss()
                    }
                }
            }
        }
    }
}