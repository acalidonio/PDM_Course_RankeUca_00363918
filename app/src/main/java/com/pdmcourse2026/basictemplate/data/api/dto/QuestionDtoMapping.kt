package com.pdmcourse2026.basictemplate.data.api.dto

import com.pdmcourse2026.basictemplate.data.database.entities.QuestionEntity

fun QuestionDto.toEntity(): QuestionEntity {
    return QuestionEntity(
        id = id,
        title = text
    )
}
