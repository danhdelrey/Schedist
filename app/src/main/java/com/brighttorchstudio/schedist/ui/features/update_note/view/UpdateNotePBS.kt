package com.brighttorchstudio.schedist.ui.features.update_note.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.common.BasicColorTagSet
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.features.manage_tag.view.TagList
import com.brighttorchstudio.schedist.ui.features.manage_tag.view.TagPickerModal
import com.brighttorchstudio.schedist.ui.features.update_note.view_model.UpdateNoteViewModel
import com.brighttorchstudio.schedist.ui.shared_view.StyledTextField
import com.brighttorchstudio.schedist.ui.shared_view.schedule.DatePickerModal
import com.brighttorchstudio.schedist.ui.shared_view.schedule.FormattedDateText
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNotePBS(
    viewModel: UpdateNoteViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onDismiss: () -> Unit,
    note: Note? = null
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val focusRequester = remember { FocusRequester() }

    var inputNoteTitle by remember { mutableStateOf(note?.title ?: "") }
    var inputNoteDescription by remember { mutableStateOf(note?.description ?: "") }
    var inputNoteTags by remember { mutableStateOf(note?.tags ?: emptyList()) }
    var inputNoteDateTime by remember {
        mutableStateOf(
            note?.dateTime ?: LocalDateTime.of(
                0,
                1,
                1,
                0,
                0
            )
        )
    }

    var haveDate by remember {
        mutableStateOf(
            if (note != null) !(DateTimeHelper.isEqual(inputNoteDateTime))
            else false
        )
    }

    var showDatePicker by remember { mutableStateOf(false) }

    var showTagPicker by remember { mutableStateOf(false) }

    val tempTag: List<Tag> = listOf(
        Tag("a", "Hoa hoc", BasicColorTagSet.PURPLE.color.toArgb().toLong()),
        Tag("a", "Mua sắm", BasicColorTagSet.PINK.color.toArgb().toLong()),
        Tag("a", "truong hoc", BasicColorTagSet.GREEN.color.toArgb().toLong()),
        Tag("a", "Hoa hoc", BasicColorTagSet.PURPLE.color.toArgb().toLong()),
        Tag("a", "Mua sắm", BasicColorTagSet.PINK.color.toArgb().toLong()),
        Tag("a", "truong hoc", BasicColorTagSet.GREEN.color.toArgb().toLong()),
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    if (showDatePicker) {
        DatePickerModal(
            initialDate = if (haveDate) inputNoteDateTime!!.toLocalDate() else LocalDate.now(),
            onDismiss = { showDatePicker = false },
            onDateSelected = {
                inputNoteDateTime = it.atStartOfDay()
                haveDate = true
                //Log.i(TAG,inputNoteDateTime.toString())
            },
            clearDate = {
                haveDate = false
                showDatePicker = false
                inputNoteDateTime = LocalDateTime.of(0, 1, 1, 0, 0)
            }
        )
    }

    if (showTagPicker) {
        TagPickerModal(
            onDismiss = { showTagPicker = false },
            tagInUse = tempTag
        )
    }


    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        }
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            //verticalArrangement = Arrangement.SpaceBetween
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

                    if (haveDate) FormattedDateText(inputNoteDateTime)

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        enabled = inputNoteTitle.isNotBlank() || inputNoteDescription.isNotBlank(),
                        onClick = {
                            onDismiss()
                            if (note != null) {
                                viewModel.updateNote(
                                    note = note,
                                    newNote = Note(
                                        id = note.id,
                                        title = inputNoteTitle,
                                        description = inputNoteDescription,
                                        tags = inputNoteTags,
                                        dateTime = inputNoteDateTime,
                                        dateCreated = note.dateCreated
                                    ),
                                    snackbarHostState = snackbarHostState
                                )
                            } else {
                                viewModel.addNote(
                                    note = Note(
                                        id = UUID.randomUUID().toString(),
                                        title = inputNoteTitle,
                                        description = inputNoteDescription,
                                        tags = inputNoteTags,
                                        dateTime = inputNoteDateTime,
                                        dateCreated = LocalDateTime.now()
                                    ),
                                    snackbarHostState = snackbarHostState
                                )
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.check),
                            contentDescription = null
                        )
                    }
                }
                Row {
                    StyledTextField(
                        value = inputNoteTitle,
                        onValueChange = { inputNoteTitle = it },
                        placeholderText = "Nhập tiêu đề...",
                        textStyle = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        modifier = Modifier
                            .focusRequester(focusRequester)
                    )
                }
                Row {
                    StyledTextField(
                        value = inputNoteDescription,
                        onValueChange = { inputNoteDescription = it },
                        placeholderText = "Nhập mô tả...",
                        textStyle = MaterialTheme.typography.bodyLarge,
                        maxLines = 23
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer
                    )
                    .padding(horizontal = 10.dp)
            ) {
                Column {
                    if (note != null)
                        TagList(
                            onClick = { showTagPicker = true },
                            tagList = tempTag
                        )

                    Row {
                        IconButton(
                            onClick = { showTagPicker = true }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.tag),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                        IconButton(
                            onClick = { showDatePicker = true }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.calendar_alt),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
    }
}