package com.brighttorchstudio.schedist.core.common

import androidx.compose.ui.graphics.Color

//dùng class enum để lưu trữ mức độ quan trọng để dễ dàng truy cập màu sắc và tên
enum class ImportanceLevel(val color: Color) {
    VERY_IMPORTANT(veryImportantTask),
    IMPORTANT(importantTask),
    NORMAL(normalTask),
    NOT_IMPORTANT(trivialTask);

    fun getDisplayName(): String {
        return when (this) {
            VERY_IMPORTANT -> "Rất quan trọng"
            IMPORTANT -> "Quan trọng"
            NORMAL -> "Bình thường"
            NOT_IMPORTANT -> "Không quan trọng"
        }
    }
}

enum class NotificationPermissionState {
    GRANTED_AND_ENABLED,
    GRANTED_BUT_DISABLED,
    DENIED
}