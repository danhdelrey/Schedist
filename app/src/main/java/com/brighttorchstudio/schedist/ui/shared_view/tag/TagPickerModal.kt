package com.brighttorchstudio.schedist.ui.shared_view.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.common.BasicColorTagSet
import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.shared_view.schedule.FormattedDateText
import java.time.LocalDateTime
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagPickerModal(
    tagInUse : List<Tag>,
    onDismiss: () -> Unit
){
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val tempTag : List<Tag> = listOf(
        Tag("a", "nhan a", BasicColorTagSet.PURPLE.color.toArgb().toLong()),
        Tag("a", "Tet", BasicColorTagSet.PINK.color.toArgb().toLong()),
        Tag("a", "nhan b", BasicColorTagSet.GREEN.color.toArgb().toLong()),
        Tag("a", "Hoa hoc", BasicColorTagSet.PURPLE.color.toArgb().toLong()),
        Tag("a", "Mua sắm", BasicColorTagSet.PINK.color.toArgb().toLong()),
        Tag("a", "truong hoc", BasicColorTagSet.GREEN.color.toArgb().toLong()),
        Tag("a", "Hoa hoc", BasicColorTagSet.PURPLE.color.toArgb().toLong()),
        Tag("a", "Mua sắm", BasicColorTagSet.PINK.color.toArgb().toLong()),
        Tag("a", "truong hoc", BasicColorTagSet.GREEN.color.toArgb().toLong()),
        Tag("a", "Hoa hoc", BasicColorTagSet.PURPLE.color.toArgb().toLong()),
        Tag("a", "Mua sắm", BasicColorTagSet.PINK.color.toArgb().toLong()),
        Tag("a", "truong hoc", BasicColorTagSet.GREEN.color.toArgb().toLong()),
    )

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween)
                {
                    IconButton(
                        onClick = {onDismiss()}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.chevron_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.check),
                            contentDescription = null
                        )
                    }
                }

                TagList(
                    tagList = tempTag,
                    checkTag = tagInUse,
                    pickMode = true,
                    fullMode = true
                )
            }
        }
    }
}