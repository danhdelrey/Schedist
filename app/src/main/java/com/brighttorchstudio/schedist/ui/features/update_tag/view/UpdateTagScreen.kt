package com.brighttorchstudio.schedist.ui.features.update_tag.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.common.TagColor
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.features.update_tag.view_model.UpdateTagViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text("Mau")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                contentAlignment = Alignment.Center

            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    enumValues<TagColor>().forEach {
                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = CircleShape
                                )
                                .background(
                                    color = it.color

                                )
                                .size(44.dp)
                                .clickable(
                                    onClick = {}
                                )
                                .border(
                                    width = 4.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                        ) {

                        }
                    }
                }
            }
        }
    }
}