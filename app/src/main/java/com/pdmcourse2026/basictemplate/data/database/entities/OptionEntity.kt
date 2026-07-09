package com.pdmcourse2026.basictemplate.data.database.entities

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.pdmcourse2026.basictemplate.model.Option

@Entity(
    tableName = "options",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("questionId")]
)
data class OptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val value: String,
    val imageUrl: String?,
    val questionId: Int,
)

fun OptionEntity.toModel(): Option {
    return Option(
        id = id,
        value = value,
        imageUrl = imageUrl,
        questionId = questionId,
    )
}