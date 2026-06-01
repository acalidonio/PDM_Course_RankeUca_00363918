package com.pdmcourse2026.basictemplate.data.api.dto

import com.pdmcourse2026.basictemplate.model.Error
import com.pdmcourse2026.basictemplate.model.Option
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto (
    var ok : Boolean,
    var message : String
)

fun ErrorDto.toModel(): Error {
    return Error(
        message = message,
        ok = ok
    )
}