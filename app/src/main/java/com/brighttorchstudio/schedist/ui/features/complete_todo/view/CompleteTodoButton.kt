package com.brighttorchstudio.schedist.ui.features.complete_todo.view

import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.ui.features.complete_todo.view_model.CompleteTodoViewModel
import kotlinx.coroutines.delay

//Hiển thị một Raddio buton mang chức năng hoàn thành todo
@Composable
fun CompleteTodoButton(
    viewModel: CompleteTodoViewModel = hiltViewModel(), //viewmodel chức năng tương ứng
    todo: Todo, //todo đưuọc gắn với nút này
    snackBarHostState: SnackbarHostState,
    onCompletedTodo: () -> Unit = {} //thực hiện sau khi hoàn thành todo
) {
    var isChecked by remember { mutableStateOf(false) }

    //LaunchedEffect có key là isChecked sẽ được gọi để thực hiện các đoạn code bên trong khi lần composition đầu tiên và mỗi khi isChecked thay đổi
    LaunchedEffect(isChecked) {
        if (isChecked) {
            delay(100) //delay 0.1s để cho cái animation hoàn thành trước khi nó bị xóa
            viewModel.completeTodo(todo)
            viewModel.showCompletedTodoSnackbar(
                snackbarHostState = snackBarHostState
            )
            isChecked =
                false //phải reset để tránh trường hợp jetpack compose tái sử dụng isChecked cho các todo phía sau trong danh sách, dẫn đến việc xóa todo đầu tiên xong rồi thì todo thứ 2 tự dưng nó được checked

            onCompletedTodo() //thực hiện hàm được truyền vào
        }
    }

    RadioButton(
        selected = isChecked,
        onClick = {
            isChecked = true
        }
    )
}