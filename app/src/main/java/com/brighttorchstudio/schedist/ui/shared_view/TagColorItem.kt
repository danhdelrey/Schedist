package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.core.common.TagColor

@Composable
fun TagColorItem(
    tagColor: TagColor,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(
                shape = CircleShape
            )
            .background(
                color = tagColor.color


            )
            .size(44.dp)
            .clickable(
                onClick = onClick
            )
            .border(
                width = if (selected) 4.dp else 0.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    ) {

    }
}