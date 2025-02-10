package com.brighttorchstudio.schedist.core.navigation

//Lưu các route vào một sealed class để dễ dàng lấy tên route, không cần nhập thủ công (giống như enum class)
sealed class AppScreens(val route: String) {
    object ManageTodoScreen :
        AppScreens("manage_todo_screen") //dùng object để cho chỉ có 1 instance duy nhất tồn tại

    object ManageNoteScreen : AppScreens("manage_note_screen")
    object ProfileScreen : AppScreens("profile_screen")

}