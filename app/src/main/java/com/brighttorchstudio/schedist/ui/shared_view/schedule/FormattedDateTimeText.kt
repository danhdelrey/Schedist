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
import java.time.LocalDateTime

//Hiển thị một text chứa datetime đã được format thành dạng HH:mm dd/MM/yyyy
@Composable
fun FormattedTimeText(
    dateTime: LocalDateTime, //datetime cần format
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
            .padding(horizontal = 17.dp)
    )
}