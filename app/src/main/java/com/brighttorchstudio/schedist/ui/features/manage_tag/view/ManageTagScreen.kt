package com.brighttorchstudio.schedist.ui.features.manage_tag.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.ui.features.manage_tag.view_model.ManageTagViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTagScreen(
    navController: NavHostController,
    viewModel: ManageTagViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Danh sách nhãn")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "menu"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Text(text = "Manage Tag Screen", modifier = Modifier.padding(innerPadding))
    }
}