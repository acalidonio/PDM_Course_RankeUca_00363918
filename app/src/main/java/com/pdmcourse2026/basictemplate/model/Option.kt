package com.pdmcourse2026.basictemplate.model

import com.pdmcourse2026.basictemplate.data.database.entities.OptionEntity

data class Option (
    val id : Int = 0,
    val imageUrl : String? = null,
    val value : String,
    val votes : Int = 0,
    val questionId: Int = 0,
)


fun Option.toEntity(): OptionEntity {
    return OptionEntity(
        id = id,
        value = value,
        imageUrl = imageUrl,
        questionId = questionId
    )
}
