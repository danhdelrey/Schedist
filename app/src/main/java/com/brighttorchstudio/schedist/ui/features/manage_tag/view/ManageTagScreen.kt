package com.brighttorchstudio.schedist.ui.features.manage_tag.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.navigation.AppScreens
import com.brighttorchstudio.schedist.ui.features.manage_tag.view_model.ManageTagViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTagScreen(
    navController: NavHostController,
    viewModel: ManageTagViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Column(
            ) {
                TopAppBar(
                    title = {
                        Text("Danh sách nhãn")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.chevron_left),
                                contentDescription = "menu"
                            )
                        }
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    value = "",
                    onValueChange = {},
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    maxLines = 1,
                    placeholder = {
                        Text("Tìm nhãn...")
                    },
                    shape = MaterialTheme.shapes.extraLarge
                )
                TextButton(
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    onClick = {}
                ) {
                    Icon(
                        modifier = Modifier.width(15.dp),
                        painter = painterResource(R.drawable.plus),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Thêm nhãn")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp)
        ) {
            when (uiState) {
                is ManageTagViewModel.UiState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ManageTagViewModel.UiState.Success -> {
                    val tagList = (uiState as ManageTagViewModel.UiState.Success).tagList
                    if (tagList.isNotEmpty()) {
                        tagList.forEach {
                            TagItem(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                tag = it,
                                selected = true,
                                showLeadingIcon = true,
                                textStyle = MaterialTheme.typography.titleMedium,
                                foregroundColor = Color.White,
                            ) {
                                navController.navigate(AppScreens.UpdateTagScreen.createRoute(it))
                            }
                        }
                    }
                }

                is ManageTagViewModel.UiState.Error -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

        }
    }
}