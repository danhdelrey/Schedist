package com.brighttorchstudio.schedist.ui.features.manage_tag.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.data.tag.model.Tag

@Composable
fun TagItem(
    modifier: Modifier = Modifier,
    showLeadingIcon: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    tag: Tag,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 5.dp, end = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (selected) tag.color.color else Color.Transparent,
                    shape = MaterialTheme.shapes.small
                )
                .border(
                    width = 1.dp,
                    color = tag.color.color,
                    shape = MaterialTheme.shapes.small
                )
                .clickable(
                    onClick = onClick
                )
                .then(modifier)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
            ) {
                if (showLeadingIcon) {
                    Icon(
                        painter = painterResource(R.drawable.tag),
                        contentDescription = null,
                        tint = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = tag.name,
                    modifier = Modifier.padding(8.dp),
                    style = textStyle,
                    color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

}