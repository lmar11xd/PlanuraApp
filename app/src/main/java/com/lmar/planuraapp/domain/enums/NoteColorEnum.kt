package com.lmar.planuraapp.domain.enums

enum class NoteColorEnum(val base: Long, val container: Long) {
    DEFAULT(0xFF5D5D5D, 0xFFEDEDED),
    GREEN(0xFF4CAF50, 0xFFC8E6C9),
    RED(0xFFF44336, 0xFFFFCDD2),
    BLUE(0xFF2196F3, 0xFFBBDEFB),
    YELLOW(0xFFFFEB3B, 0xFFFFF9C4)
}