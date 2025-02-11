package com.brighttorchstudio.schedist.ui.features.update_tag.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.features.update_tag.view_model.UpdateTagViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTagScreen(
    viewModel: UpdateTagViewModel = hiltViewModel(),
    navController: NavHostController,
    tag: Tag? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.check),
                            contentDescription = "menu"
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "menu"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = "menu"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Text("hello", modifier = Modifier.padding(innerPadding))
    }
}