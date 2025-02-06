package com.brighttorchstudio.schedist.data.note_with_tags.model

import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.data.tag.model.Tag

data class NoteWithTags(
    val note: Note,
    val tags: List<Tag>
)
