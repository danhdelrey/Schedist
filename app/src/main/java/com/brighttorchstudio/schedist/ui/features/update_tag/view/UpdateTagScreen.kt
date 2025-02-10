package com.brighttorchstudio.schedist.ui.features.update_tag.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.features.update_tag.view_model.UpdateTagViewModel

@Composable
fun UpdateTagScreen(
    viewModel: UpdateTagViewModel = hiltViewModel(),
    navController: NavHostController,
    tag: Tag? = null
) {
    Scaffold { innerPadding ->
        if (tag != null) {
            Text(text = tag.name, modifier = Modifier.padding(innerPadding))
        } else {
            Text(text = "Tag is null", modifier = Modifier.padding(innerPadding))

        }
    }
}