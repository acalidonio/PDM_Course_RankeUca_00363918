package com.pdmcourse2026.basictemplate.data.database.entities

import androidx.room3.Embedded
import androidx.room3.Relation
import com.pdmcourse2026.basictemplate.model.Question

data class QuestionWithOptions(
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val options: List<OptionEntity>
)

fun QuestionWithOptions.toModel(): Question {
    return Question(
        id = question.id,
        title = question.title,
        optionCount = options.size,
    )
}