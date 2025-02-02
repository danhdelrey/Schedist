package com.brighttorchstudio.schedist.ui.features.manage_note.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.common.BasicColorTagSet
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.features.manage_note.view_model.ManageNoteViewModel
import com.brighttorchstudio.schedist.ui.shared_view.schedule.FormattedDateText
import com.brighttorchstudio.schedist.ui.shared_view.tag.TagList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    note: Note,
    viewModel : ManageNoteViewModel = hiltViewModel()
){
    var showNoteDetail by remember { mutableStateOf(false) }
    val inSelectMode by viewModel.inSelectMode.collectAsStateWithLifecycle()
    val selectedNotesForPerformingActions by viewModel.selectedNotesForPerformingAction.collectAsStateWithLifecycle()

    var haveDate = !DateTimeHelper.isEqual(note.dateTime)

    val tempTag : List<Tag> = listOf(
        Tag("a", "Hoa hoc", BasicColorTagSet.PURPLE.color.toArgb().toLong()),
        Tag("a", "Mua sắm", BasicColorTagSet.PINK.color.toArgb().toLong()),
        Tag("a", "truong hoc", BasicColorTagSet.GREEN.color.toArgb().toLong()),
        Tag("a", "Hoa hoc", BasicColorTagSet.PURPLE.color.toArgb().toLong()),
        Tag("a", "Mua sắm", BasicColorTagSet.PINK.color.toArgb().toLong()),
        Tag("a", "truong hoc", BasicColorTagSet.GREEN.color.toArgb().toLong()),
    )

    Box(
        modifier = Modifier.fillMaxSize()
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
    ){
        Column(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ){
                if (inSelectMode){
                    Checkbox(
                        checked = note in selectedNotesForPerformingActions,
                        onCheckedChange = {
                            viewModel.onNoteClicked(note)
                        }
                    )
                }
                else
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.sticky_note),
                            contentDescription = null,

                            )
                    }

                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f),
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
                        else{
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

                    if (haveDate){
                        Row{
                            FormattedDateText(
                                date=note.dateTime,
                                color = MaterialTheme.colorScheme.outline)
                        }
                    }
                }

                IconToggleButton(
                    checked = showNoteDetail,
                    onCheckedChange = {showNoteDetail = it}
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

            AnimatedVisibility(visible = !showNoteDetail) {
                Row(modifier = Modifier.padding(horizontal = 14.dp)){
                    TagList(
                        tagList = tempTag
                    )
                }

            }

            AnimatedVisibility(visible = (showNoteDetail && (note.description.isNotEmpty() || note.tags.isNotEmpty()))) {
                Column(modifier = Modifier.padding(horizontal = 14.dp)){
                    Text(
                        text = note.description,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    TagList(
                        fullMode = true,
                        tagList = tempTag
                    )
                }
            }
        }

    }

}