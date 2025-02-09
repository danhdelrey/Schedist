package com.brighttorchstudio.schedist.ui.features.manage_tag.view

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.shared_view.EllipsisBox

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagList(
    tagList: List<Tag>,
    checkTag: List<Tag>? = emptyList(),
    onClick: () -> Unit = {},
    fullMode: Boolean? = false,
    pickMode: Boolean? = false
) {
    var maxLine = 1
    if (fullMode!!) {
        maxLine = Int.MAX_VALUE
    }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        maxLines = maxLine,
        overflow = FlowRowOverflow.expandIndicator {
            EllipsisBox()
        }
    ) {
        tagList.forEach { tag ->
            TagItem(
                inUse = if (pickMode!!) (tag in checkTag!!) else true,
                tag = tag
            )
        }

    }
}