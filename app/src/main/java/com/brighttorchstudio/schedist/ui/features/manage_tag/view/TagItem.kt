package com.brighttorchstudio.schedist.ui.features.manage_tag.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.data.tag.model.Tag

@Composable
fun TagItem(
    tag : Tag,
    inUse : Boolean? = true
){
    Column(
        modifier = Modifier
            .padding(top = 5.dp, end = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if(inUse!!) Color(tag.color) else Color.Transparent,
                    shape = MaterialTheme.shapes.small
                )
                .border(
                    width = 1.dp,
                    color = Color(tag.color),
                    shape = MaterialTheme.shapes.small
                )
        ) {
            Text(
                text = tag.name,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

}