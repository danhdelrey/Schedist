package com.brighttorchstudio.schedist.core.navigation

sealed class AppScreens(val route: String) {
    object ManageTodoScreen : AppScreens("manage_todo_screen")
    object ManageNoteScreen : AppScreens("manage_note_screen")
}