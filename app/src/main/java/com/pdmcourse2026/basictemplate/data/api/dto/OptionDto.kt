package com.pdmcourse2026.basictemplate.data.api.dto

import com.pdmcourse2026.basictemplate.data.database.entities.OptionEntity
import com.pdmcourse2026.basictemplate.model.Option
import kotlinx.serialization.Serializable

@Serializable
data class OptionDto (
    val id : Int,
    val imageUrl : String? = null,
    val value : String,
    val votes : Int? = 0,
    val questionId: Int? = null
)

fun OptionDto.toEntity(questionId: Int): OptionEntity {
    return OptionEntity(
        id = id,
        imageUrl = imageUrl,
        value = value,
        questionId = this.questionId ?: questionId
    )
}

fun OptionDto.toModel(): Option {
    return Option(
        id = id,
        imageUrl = imageUrl,
        value = value,
        votes = votes ?: 0
    )
}