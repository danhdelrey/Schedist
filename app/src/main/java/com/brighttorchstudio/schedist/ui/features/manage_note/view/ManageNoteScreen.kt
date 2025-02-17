package com.brighttorchstudio.schedist.ui.features.manage_note.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.core.navigation.BottomNavigationBar
import com.brighttorchstudio.schedist.ui.features.delete_note.view.DeleteNoteButton
import com.brighttorchstudio.schedist.ui.features.manage_note.view_model.ManageNoteViewModel
import com.brighttorchstudio.schedist.ui.features.update_note.view.FABAddNote
import com.brighttorchstudio.schedist.ui.features.update_note.view.UpdateNotePBS
import com.brighttorchstudio.schedist.ui.shared_view.BottomActionBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageNoteScreen(
    viewModel: ManageNoteViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val inSelectMode by viewModel.inSelectMode.collectAsStateWithLifecycle()
    val topBarText = if (!inSelectMode) {
        "Danh sách ghi chú"
    } else {
        ""
    }

    val selectedNoteForUpdating by viewModel.selectedNoteForUpdating.collectAsStateWithLifecycle()
    val selectedNotesForPerformingAction by viewModel.selectedNotesForPerformingAction.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    val selectAllNotes by viewModel.selectAllNotes.collectAsStateWithLifecycle()

    if (selectedNoteForUpdating != null) {
        UpdateNotePBS(
            note = selectedNoteForUpdating,
            snackbarHostState = snackbarHostState,
            onDismiss = { viewModel.cancelNoteUpdate() }
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        topBar = {
            TopAppBar(
                actions = {
                    if (inSelectMode) {
                        if (!selectAllNotes)
                            TextButton(
                                onClick = { viewModel.onSelectAllNotesClicked() }
                            ) {
                                Text("Chọn tất cả")
                            }
                        else
                            TextButton(
                                onClick = { viewModel.onDeselectAllNotesClicked() }
                            ) {
                                Text("Bỏ chọn tất cả")
                            }
                    }
                },

                title = { Text(topBarText) },
                navigationIcon = {
                    if (inSelectMode) {
                        TextButton(
                            onClick = { viewModel.exitSelectMode() }
                        ) {
                            Text("Quay lại")
                        }
                    }
//                    else
//                        IconButton(
//                            onClick = {}
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Menu,
//                                contentDescription = "menu"
//                            )
//                        }
                }
            )
        },

        floatingActionButton = {
            FABAddNote(snackbarHostState)
        },

        bottomBar = {
            if (inSelectMode) {
                BottomActionBar(
                    action1 = {
                        DeleteNoteButton(
                            toBeDeletedNoteList = selectedNotesForPerformingAction.toList(),
                            onDeletedNote = { viewModel.exitSelectMode() },
                            snackBarHostState = snackbarHostState,
                            deleteAllNote = selectAllNotes
                        )
                    }

                )
            } else {
                BottomNavigationBar(navController)
            }
        }

    ) { innerPadding ->
        when (uiState) {
            is ManageNoteViewModel.UiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CircularProgressIndicator()
                }
            }

            is ManageNoteViewModel.UiState.Success -> {
                val noteList = (uiState as ManageNoteViewModel.UiState.Success).notes
                if (noteList.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Text(
                            text = "Chạm vào biểu tượng + để thêm ghi chú mới.",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.outline
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(200.dp)
                        )
                    }
                } else {
                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Column() {
                            noteList.forEach { note ->
                                NoteItem(note)
                            }
                        }
                    }
                }
            }

            is ManageNoteViewModel.UiState.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CircularProgressIndicator()
                }
            }
        }

    }

}