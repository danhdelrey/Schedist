package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.helpers.DateTimeHelper
import java.time.LocalDateTime

@Composable
fun FormattedTimeText(
    dateTime: LocalDateTime,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier,
) {
    Text(
        text = DateTimeHelper.formatLocalDateTime(dateTime),
        style = MaterialTheme.typography.labelLarge.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = modifier
            .padding(horizontal = 20.dp)
    )
}