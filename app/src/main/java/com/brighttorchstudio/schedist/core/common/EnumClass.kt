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
    // Grays
    CHARCOAL_GRAY(Color(0xFF333333)),
    DIM_GRAY(Color(0xFF696969)),
    GRAY(Color(0xFF808080)),

    // Blues
    MIDNIGHT_BLUE(Color(0xFF000033)),
    NAVY_BLUE(Color(0xFF000066)),
    DARK_BLUE(Color(0xFF00008B)),
    MEDIUM_BLUE(Color(0xFF0000CD)),
    BLUE(Color(0xFF0000FF)),
    ROYAL_BLUE(Color(0xFF4169E1)),
    STEEL_BLUE(Color(0xFF4682B4)),
    DODGER_BLUE(Color(0xFF1E90FF)),
    DEEP_SKY_BLUE(Color(0xFF00BFFF)),


    // Greens
    KASHMIR_GREEN(Color(0xFF003300)),
    FOREST_GREEN(Color(0xFF006600)),
    DARK_GREEN(Color(0xFF006400)),
    GREEN(Color(0xFF008000)),
    SEA_GREEN(Color(0xFF2E8B57)),
    MEDIUM_SEA_GREEN(Color(0xFF3CB371)),
    SPRING_GREEN(Color(0xFF00FF7F)),
    MEDIUM_SPRING_GREEN(Color(0xFF00FA9A)),
    LIME_GREEN(Color(0xFF32CD32)),
    LIGHT_GREEN(Color(0xFF90EE90)),
    PALE_GREEN(Color(0xFF98D898)),


    // Reds & Browns
    MAROON(Color(0xFF800000)),
    DARK_RED(Color(0xFF8B0000)),
    FIREBRICK(Color(0xFFB22222)),
    CRIMSON(Color(0xFFDC143C)),
    RED(Color(0xFFFF0000)),
    INDIAN_RED(Color(0xFFCD5C5C)),
    LIGHT_CORAL(Color(0xFFF08080)),
    SALMON(Color(0xFFFA8072)),
    DARK_SALMON(Color(0xFFE9967A)),
    CORAL(Color(0xFFFF7F50)),
    TOMATO(Color(0xFFFF6347)),
    ORANGE_RED(Color(0xFFFF4500)),
    ORANGISH_RED(Color(0xFFE25822)), // Custom
    BROWN(Color(0xFFA52A2A)),
    SADDLE_BROWN(Color(0xFF8B4513)),
    SIENNA(Color(0xFFA0522D)),
    CHOCOLATE(Color(0xFFD2691E)),


    // Purples
    INDIGO(Color(0xFF4B0082)),
    DARK_PURPLE(Color(0xFF330033)), // Custom dark purple
    PURPLE(Color(0xFF800080)),
    DARK_MAGENTA(Color(0xFF8B008B)),
    DARK_VIOLET(Color(0xFF9400D3)),
    DARK_ORCHID(Color(0xFF9932CC)),
    MEDIUM_ORCHID(Color(0xFFBA55D3)),
    BLUE_VIOLET(Color(0xFF8A2BE2)),
    MEDIUM_PURPLE(Color(0xFF9370D8)),
    PLUM(Color(0xFFDDA0DD)),
    VIOLET(Color(0xFFEE82EE)),


    // Yellows & Oranges
    GOLD(Color(0xFFFFD700)),
    YELLOW(Color(0xFFFFFF00)),
    KHAKI(Color(0xFFF0E68C)),
    DARK_KHAKI(Color(0xFFBDB76B)),
    GOLDENROD(Color(0xFFDAA520)),
    ORANGE(Color(0xFFFFA500)),
    DARK_ORANGE(Color(0xFFFF8C00)),

    // Cyans & Teals
    DARK_CYAN(Color(0xFF008B8B)),
    TEAL(Color(0xFF008080)),
    DARK_TURQUOISE(Color(0xFF00CED1)),
    CADET_BLUE(Color(0xFF5F9EA0)),
    DARK_SLATE_BLUE(Color(0xFF483D8B)),
    MEDIUM_TURQUOISE(Color(0xFF48C9B0)), // Custom
    AQUAMARINE(Color(0xFF7FFFD4)),
    TURQUOISE(Color(0xFF40E0D0)),
    PALE_TURQUOISE(Color(0xFFAFEEEE)),

    // Other
    OLIVE(Color(0xFF808000)),
    OLIVE_DRAB(Color(0xFF6B8E23)),
    YELLOW_GREEN(Color(0xFF9ACD32)),
    DARK_OLIVE_GREEN(Color(0xFF556B2F)),
    PERU(Color(0xFFCD853F)),
    ROSY_BROWN(Color(0xFFBC8F8F)),
    SANDY_BROWN(Color(0xFFF4A460)),
}
