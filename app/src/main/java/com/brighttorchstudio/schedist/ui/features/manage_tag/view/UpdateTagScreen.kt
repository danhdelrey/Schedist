package com.brighttorchstudio.schedist.ui.features.manage_tag.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.common.TagColor
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.features.manage_tag.view_model.ManageTagViewModel
import com.brighttorchstudio.schedist.ui.shared_view.ActionsDropdown
import com.brighttorchstudio.schedist.ui.shared_view.TagColorItem
import kotlinx.serialization.json.Json
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun UpdateTagScreen(
    viewModel: ManageTagViewModel = hiltViewModel(),
    navController: NavHostController,
    tag: Tag? = null
) {

    var selectedColor by remember { mutableStateOf(tag?.color ?: TagColor.CHARCOAL_GRAY) }
    var tagNameInput by remember { mutableStateOf(tag?.name ?: "") }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { },
                    actions = {
                        IconButton(
                            onClick = {
                                if (tag != null) {
                                    viewModel.updateTag(
                                        Tag(
                                            id = tag.id,
                                            name = tagNameInput,
                                            color = selectedColor,
                                        )
                                    )
                                    navController.previousBackStackEntry?.savedStateHandle?.set(
                                        "tagUpdated",
                                        Json.encodeToString(tag)
                                    )
                                    navController.popBackStack()
                                } else {
                                    val newTag = Tag(
                                        id = UUID.randomUUID().toString(),
                                        name = tagNameInput,
                                        color = selectedColor,
                                    )
                                    viewModel.addTag(newTag)

                                    navController.previousBackStackEntry?.savedStateHandle?.set(
                                        "tagAdded",
                                        Json.encodeToString(newTag)
                                    )
                                    navController.popBackStack()
                                }

                            },
                            enabled = tagNameInput.isNotEmpty(),
                            colors = IconButtonDefaults.iconButtonColors().copy(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.check),
                                contentDescription = "menu",
                            )
                        }
                        ActionsDropdown(
                            onDeleteActionRequested = {
                                if (tag != null) {
                                    viewModel.deleteTag(tag)
                                    navController.previousBackStackEntry?.savedStateHandle?.set(
                                        "tagDeleted",
                                        Json.encodeToString(tag)
                                    )
                                    navController.popBackStack()
                                } else {
                                    navController.popBackStack()
                                }
                            }
                        )
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
                        .padding(horizontal = 10.dp),
                    value = tagNameInput,
                    onValueChange = {
                        if (it.length <= 20) tagNameInput = it
                    },
                    shape = CircleShape,
                    colors = TextFieldDefaults.colors().copy(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = selectedColor.color,
                        unfocusedContainerColor = selectedColor.color,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    placeholder = {
                        Text("Nhập tên nhãn tối đa 20 kí tự...")
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.tag),
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    maxLines = 1,
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text(
                "Màu",
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
                style = MaterialTheme.typography.titleMedium
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
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