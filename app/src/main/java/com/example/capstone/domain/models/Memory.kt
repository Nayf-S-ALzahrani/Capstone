package com.example.capstone.domain.models

import java.util.*

data class Memory(
    var id: UUID = UUID.randomUUID(),
    val title: String = "",
    val imageUrl: String = "",
    val date: Date? = Date(),
    val description: String = "",
    val type: String = ""
    )


//val location: ?
//val voiceNote: ?
//val categoryId: ?