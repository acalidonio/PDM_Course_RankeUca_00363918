package com.pdmcourse2026.basictemplate.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class VoteDto (
    val question: QuestionDto,
    val votes: Int
)

@Serializable
data class QuestionsDto(
    val id: Int,
    val text: String,
    val questions: List<VoteDto>
)