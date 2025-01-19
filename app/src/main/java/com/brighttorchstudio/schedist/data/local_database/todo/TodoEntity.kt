package com.brighttorchstudio.schedist.data.local_database.todo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brighttorchstudio.schedist.core.common.ImportanceLevel
import java.time.LocalDateTime

//Đại diện cho một bảng
//Nếu thay đổi các trường thì phải xóa dữ liệu của ứng dụng trên điện thoại xong chạy lại chương trình, không thôi sẽ bị lỗi "Room cannot verify...."
//Room chỉ có thể lưu các kiểu dữ liệu nguyên thủy như String, boolean, long,...
@Entity
data class TodoEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val importanceLevel: ImportanceLevel, //Đây không phải kiểu dữ liệu được Room hỗ trợ nên phải dùng một cái gì đó để biến nó thành kiểu dữ liệu được Room hỗ trợ
    val dateTime: LocalDateTime, //Đây không phải kiểu dữ liệu được Room hỗ trợ nên phải dùng một cái gì đó để biến nó thành kiểu dữ liệu được Room hỗ trợ
    val reminderEnabled: Boolean,
)



