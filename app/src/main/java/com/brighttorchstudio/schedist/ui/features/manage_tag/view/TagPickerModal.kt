package com.brighttorchstudio.schedist.ui.features.manage_tag.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.features.manage_tag.view_model.ManageTagViewModel
import com.brighttorchstudio.schedist.ui.shared_view.EllipsisBox

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TagPickerModal(
    viewModel: ManageTagViewModel = hiltViewModel(),
    selectedTags: List<Tag> = emptyList(),
    onDismiss: () -> Unit
) {

    val tagList by viewModel.tagList.collectAsStateWithLifecycle()
    var currentSelectedTags by remember { mutableStateOf(selectedTags) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )



    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    IconButton(
                        onClick = { onDismiss() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.chevron_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.check),
                            contentDescription = null
                        )
                    }
                }

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    overflow = FlowRowOverflow.expandIndicator {
                        EllipsisBox()
                    }
                ) {
                    tagList.forEach { tag ->
                        TagItem(
                            tag = tag,
                            selected = tag in currentSelectedTags,
                            onToggleSelection = {
                                currentSelectedTags = if (tag in currentSelectedTags) {
                                    currentSelectedTags - tag
                                } else {
                                    currentSelectedTags + tag
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}