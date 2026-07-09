package com.pdmcourse2026.basictemplate.data.api.dto

import com.pdmcourse2026.basictemplate.model.Option
import kotlinx.serialization.Serializable

@Serializable
data class OptionDto (
    val id : Int,
    val imageUrl : String? = null,
    val name : String,
    val votes : Int
)

fun OptionDto.toModel(): Option {
    return Option(
        id = id,
        imageUrl = imageUrl,
        name = name,
        votes = votes
    )
}