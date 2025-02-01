package com.brighttorchstudio.schedist.ui.shared_view.schedule

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun FormattedDateText(
    date: LocalDateTime,
    color : Color? = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Text(
        text = DateTimeHelper.formatDateOnly(date),
        style = MaterialTheme.typography.labelLarge.copy(
            color = color!!
        ),
    )
}