package com.pdmcourse2026.basictemplate.data.repository

import com.pdmcourse2026.basictemplate.model.Option
import kotlinx.coroutines.flow.Flow

interface OptionRepository {
    fun getOptions(questionId: Int): Flow<List<Option>>
    suspend fun addOption(value: String, imageUrl: String?, questionId: Int)
    suspend fun deleteOption(option: Option)
    suspend fun updateOption(option: Option)
}