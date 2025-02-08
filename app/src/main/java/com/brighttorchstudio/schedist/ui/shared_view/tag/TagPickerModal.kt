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
import com.brighttorchstudio.schedist.data.tag.model.Tag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagPickerModal(
    tagInUse: List<Tag>,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val tempTag: List<Tag> = listOf(
        Tag("a1", "Công việc", BasicColorTagSet.BLUE_GRAY.color.toArgb().toLong()),
        Tag("a2", "Quan trọng", BasicColorTagSet.RED.color.toArgb().toLong()),
        Tag("a3", "Gia đình", BasicColorTagSet.PINK.color.toArgb().toLong()),
        Tag("a4", "Học tập", BasicColorTagSet.PURPLE.color.toArgb().toLong()),
        Tag("a5", "Sức khỏe", BasicColorTagSet.DEEP_PURPLE.color.toArgb().toLong()),
        Tag("a6", "Tài chính", BasicColorTagSet.INDIGO.color.toArgb().toLong()),
        Tag("a7", "Du lịch", BasicColorTagSet.BLUE.color.toArgb().toLong()),
        Tag("a8", "Giải trí", BasicColorTagSet.LIGHT_BLUE.color.toArgb().toLong()),
        Tag("a9", "Công nghệ", BasicColorTagSet.CYAN.color.toArgb().toLong()),
        Tag("a10", "Thiên nhiên", BasicColorTagSet.TEAL.color.toArgb().toLong()),
        Tag("a11", "Sách", BasicColorTagSet.GREEN.color.toArgb().toLong()),
        Tag("a12", "Âm nhạc", BasicColorTagSet.LIGHT_GREEN.color.toArgb().toLong()),
        Tag("a13", "Phim ảnh", BasicColorTagSet.LIME.color.toArgb().toLong()),
        Tag("a14", "Món ăn", BasicColorTagSet.YELLOW.color.toArgb().toLong()),
        Tag("a15", "Thể thao", BasicColorTagSet.AMBER.color.toArgb().toLong()),
        Tag("a16", "Mua sắm", BasicColorTagSet.ORANGE.color.toArgb().toLong()),
        Tag("a17", "Bạn bè", BasicColorTagSet.DEEP_ORANGE.color.toArgb().toLong()),
        Tag("a18", "Nghệ thuật", BasicColorTagSet.BROWN.color.toArgb().toLong()),
        Tag("a19", "Khác", BasicColorTagSet.GRAY.color.toArgb().toLong()),
    )

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    IconButton(
                        onClick = { onDismiss() }
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