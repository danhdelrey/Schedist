package com.brighttorchstudio.schedist.ui.features.update_note.view

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.brighttorchstudio.schedist.R

@Composable
fun FABAddNote(
    snackbarHostState: SnackbarHostState
){
    var showUpdateNotePBS by remember { mutableStateOf(false) }

    if (showUpdateNotePBS){
        UpdateNotePBS(
            snackbarHostState = snackbarHostState,
            onDismiss = {showUpdateNotePBS = false}
        )
    }

    FloatingActionButton(
        onClick = {showUpdateNotePBS = true},
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,

    ) {
        Icon(
            painter = painterResource(R.drawable.plus),
            contentDescription = null,
        )
    }
}