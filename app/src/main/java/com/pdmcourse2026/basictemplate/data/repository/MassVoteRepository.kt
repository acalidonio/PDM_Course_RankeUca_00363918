package com.pdmcourse2026.basictemplate.data.repository

import com.pdmcourse2026.basictemplate.data.api.dto.QuestionsDto
import com.pdmcourse2026.basictemplate.model.Option
import com.pdmcourse2026.basictemplate.model.Vote

interface MassVoteRepository {
    suspend fun vote(body: Int): Result<Error>
    suspend fun getQuestions(): Result<QuestionsDto>
}