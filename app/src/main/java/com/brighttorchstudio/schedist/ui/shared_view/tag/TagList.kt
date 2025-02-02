package com.brighttorchstudio.schedist.ui.shared_view.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.features.manage_tag.view.TagItem
import com.brighttorchstudio.schedist.ui.shared_view.EllipsisBox

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagList(
    tagList: List<Tag>,
    checkTag: List<Tag>?= emptyList(),
    onClick: () -> Unit = {},
    fullMode : Boolean? = false,
    pickMode : Boolean?= false
){
    var maxLine = 1
    if (fullMode!!) {maxLine = Int.MAX_VALUE}

        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = maxLine,
            overflow = FlowRowOverflow.expandIndicator {
                EllipsisBox()
            }
        ){
            tagList.forEach{tag ->
                TagItem(
                    inUse = if (pickMode!!) (tag in checkTag!!) else true,
                    tag = tag)
            }

        }
    }