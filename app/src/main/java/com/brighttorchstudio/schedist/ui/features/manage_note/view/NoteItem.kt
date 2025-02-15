package com.brighttorchstudio.schedist.ui.features.manage_note.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.ui.features.manage_note.view_model.ManageNoteViewModel
import com.brighttorchstudio.schedist.ui.features.manage_tag.view.TagItem
import com.brighttorchstudio.schedist.ui.shared_view.EllipsisBox
import com.brighttorchstudio.schedist.ui.shared_view.schedule.FormattedDateText

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun NoteItem(
    note: Note,
    viewModel: ManageNoteViewModel = hiltViewModel()
) {
    var showNoteDetail by remember { mutableStateOf(false) }
    val inSelectMode by viewModel.inSelectMode.collectAsStateWithLifecycle()
    val selectedNotesForPerformingActions by viewModel.selectedNotesForPerformingAction.collectAsStateWithLifecycle()

    var haveDate = !DateTimeHelper.isEqual(note.dateTime)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .background(
                MaterialTheme.colorScheme.surfaceContainerLow,
                MaterialTheme.shapes.small
            )
            .combinedClickable(
                onClick = {
                    viewModel.onNoteClicked(note)
                },
                onLongClick = {
                    viewModel.onNoteLongClicked(note)
                }
            )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (inSelectMode) {
                    Checkbox(
                        checked = note in selectedNotesForPerformingActions,
                        onCheckedChange = {
                            viewModel.onNoteClicked(note)
                        }
                    )
                } else
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.sticky_note),
                            contentDescription = null,

                            )
                    }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (note.title.isNotEmpty())
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    else {
                        Text(
                            text = "Không có tiêu đề",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.outline
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(),

                            )
                    }

                    if (haveDate) {
                        Row {
                            FormattedDateText(
                                date = note.dateTime,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }

                IconToggleButton(
                    checked = showNoteDetail,
                    onCheckedChange = { showNoteDetail = it }
                ) {
                    Icon(
                        painter =
                        if (showNoteDetail) painterResource(R.drawable.chevron_up)
                        else painterResource(R.drawable.chevron_down),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }

            AnimatedVisibility(
                visible = !showNoteDetail,
            ) {
                Row(modifier = Modifier.padding(horizontal = 14.dp)) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 1,
                        overflow = FlowRowOverflow.expandIndicator {
                            EllipsisBox()
                        }
                    ) {
                        note.tags.forEach { tag ->
                            TagItem(
                                tag = tag,
                                selected = true,
                                onClick = {
                                    viewModel.onNoteClicked(note)
                                }
                            )
                        }

                    }
                }
            }

            AnimatedVisibility(
                visible = (showNoteDetail && (note.description.isNotEmpty() || note.tags.isNotEmpty())),

                ) {
                Column(modifier = Modifier.padding(horizontal = 14.dp)) {
                    Text(
                        text = note.description,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        overflow = FlowRowOverflow.expandIndicator {
                            EllipsisBox()
                        }
                    ) {
                        note.tags.forEach { tag ->
                            TagItem(
                                tag = tag,
                                selected = true,
                                onClick = {
                                    viewModel.onNoteClicked(note)
                                }
                            )
                        }

                    }
                }
            }
        }

    }

}