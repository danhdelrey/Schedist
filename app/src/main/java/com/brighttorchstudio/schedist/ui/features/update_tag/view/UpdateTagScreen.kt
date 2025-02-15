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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.util.UUID
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun UpdateTagScreen(
    viewModel: UpdateTagViewModel = hiltViewModel(),
    navController: NavHostController,
    tag: Tag? = null
) {

    var selectedColor by remember { mutableStateOf(tag?.color ?: TagColor.BLUE_GRAY) }
    var tagNameInput by remember { mutableStateOf(tag?.name ?: "") }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { },
                    actions = {
                        IconButton(
                            onClick = {
                                if(tag != null){
                                    //cap nhat tag
                                }else{
                                    viewModel.addTag(
                                        Tag(
                                            id = UUID.randomUUID().toString(),
                                            name = tagNameInput,
                                            color = selectedColor,
                                        )
                                    )

                                    navController.popBackStack()
                                }

                            },
                            enabled = tagNameInput.isNotEmpty()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.check),
                                contentDescription = "menu",
                                tint = MaterialTheme.colorScheme.primary
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
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.arrow_left),
                                contentDescription = "menu"
                            )
                        }
                    },
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                    ,
                    value = tagNameInput,
                    onValueChange = {
                        tagNameInput = it
                    },
                    shape = CircleShape,
                    colors = TextFieldDefaults.colors().copy(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor =  Color.Transparent
                    ),
                    placeholder = {
                        Text("Nhap nhan...")
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text(
                "Mau",
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
                style = MaterialTheme.typography.titleMedium
            )

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
                        TagColorItem(
                            tagColor = it,
                            selected = selectedColor.color == it.color,
                            onClick = {
                                selectedColor = it
                            }
                        )
                    }
                }
            }
        }
    }
}