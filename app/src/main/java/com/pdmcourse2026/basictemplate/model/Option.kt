package com.pdmcourse2026.basictemplate.model

import com.pdmcourse2026.basictemplate.data.database.entities.OptionEntity

data class Option (
    val id : Int = 0,
    val imageUrl : String?,
    val name : String,
    val votes : Int = 0,
    val questionId: Int = 0,
)

fun OptionEntity.toModel(): Option {
    return Option(
        id = id,
        name = name,
        imageUrl = imageUrl,
        votes = 0
    )
}

fun Option.toEntity(): OptionEntity {
    return OptionEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        questionId = questionId
    )
}
