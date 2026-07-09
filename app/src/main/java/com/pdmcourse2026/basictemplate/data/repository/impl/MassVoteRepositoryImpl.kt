package com.pdmcourse2026.basictemplate.data.repository.impl

import com.pdmcourse2026.basictemplate.data.api.dto.QuestionsDto
import com.pdmcourse2026.basictemplate.data.repository.MassVoteRepository

class MassVoteRepositoryImpl : MassVoteRepository {
    override suspend fun vote(body: Int): Result<Error> {
        TODO("Not yet implemented")
    }

    override suspend fun getQuestions(): Result<QuestionsDto> {
        TODO("Not yet implemented")
    }
}