package com.pdmcourse2026.basictemplate.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    val id: Int,
    val text: String,
)
