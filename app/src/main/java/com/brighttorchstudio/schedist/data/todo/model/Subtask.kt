package com.brighttorchstudio.schedist.data.todo.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Subtask(
    var name : String,
    var complete : Boolean,
) {
}