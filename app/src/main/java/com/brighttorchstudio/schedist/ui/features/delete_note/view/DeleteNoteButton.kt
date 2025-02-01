package com.brighttorchstudio.schedist.ui.features.delete_note.view

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.ui.features.delete_note.view_model.DeleteNoteViewModel

@Composable
fun DeleteNoteButton(
    viewModel: DeleteNoteViewModel = hiltViewModel(),
    toBeDeletedNoteList: List<Note>,
    onDeletedNote: () -> Unit = {},
    snackBarHostState: SnackbarHostState,
    deleteAllNote : Boolean,
){
    IconButton(
        onClick = {
            if (deleteAllNote)
                viewModel.deleteAllNotes(toBeDeletedNoteList,snackBarHostState)
            else viewModel.deleteNotes(toBeDeletedNoteList,snackBarHostState)
            onDeletedNote()
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.trash_alt),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
    }
}