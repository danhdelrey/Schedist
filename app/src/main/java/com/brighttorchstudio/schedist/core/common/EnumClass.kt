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

enum class TagColor(val color: Color) {
    BLUE_GRAY(Color(0xFF607D8B)),      // Blue Gray
    RED(Color(0xFFE91E63)),            // Red
    PINK(Color(0xFFF08080)),            // Light Coral (slightly different pink)
    PURPLE(Color(0xFF9C27B0)),         // Purple
    DEEP_PURPLE(Color(0xFF673AB7)),     // Deep Purple
    INDIGO(Color(0xFF3F51B5)),         // Indigo
    BLUE(Color(0xFF2196F3)),            // Blue
    LIGHT_BLUE(Color(0xFF03A9F4)),     // Light Blue
    CYAN(Color(0xFF00BCD4)),           // Cyan
    TEAL(Color(0xFF009688)),           // Teal
    GREEN(Color(0xFF4CAF50)),           // Green
    LIGHT_GREEN(Color(0xFF8BC34A)),     // Light Green
    LIME(Color(0xFFCDDC39)),           // Lime
    YELLOW(Color(0xFFFFEB3B)),         // Yellow
    AMBER(Color(0xFFFFC107)),         // Amber
    ORANGE(Color(0xFFFF9800)),         // Orange
    DEEP_ORANGE(Color(0xFFFF5722)),     // Deep Orange
    BROWN(Color(0xFF795548)),          // Brown
    GRAY(Color(0xFF9E9E9E)),           // Gray
}
