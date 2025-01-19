package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit, //truyền giá trị đã nhập ra bên ngoài
    placeholderText: String,
    textStyle: TextStyle,
    maxLines: Int = 1, // Mặc định là 1 dòng
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        placeholder = {
            Text(
                text = placeholderText,
                style = textStyle,
                color = MaterialTheme.colorScheme.outline
            )
        },
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth()
    )
}