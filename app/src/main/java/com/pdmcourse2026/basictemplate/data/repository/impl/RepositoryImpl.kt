package com.pdmcourse2026.basictemplate.data.repository.impl

import com.pdmcourse2026.basictemplate.data.api.KtorClient
import com.pdmcourse2026.basictemplate.data.api.dto.CreateVoteRequestDto
import com.pdmcourse2026.basictemplate.data.api.dto.ErrorDto
import com.pdmcourse2026.basictemplate.data.api.dto.OptionDto
import com.pdmcourse2026.basictemplate.data.api.dto.toModel
import com.pdmcourse2026.basictemplate.data.repository.Repository
import com.pdmcourse2026.basictemplate.model.Error
import com.pdmcourse2026.basictemplate.model.Option
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class RepositoryImpl : Repository {
    override suspend fun vote(body: Int): Result<Error> {
        try {
            val request = CreateVoteRequestDto(body)
            val response: ErrorDto = KtorClient.client.post("vote") {
                setBody(request)
            }.body()

            return Result.success(response.toModel())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getOptions(): Result<List<Option>> {
        try {
            val response: List<OptionDto> = KtorClient.client.get("options") {}.body()

            return Result.success(response.map { optionDto -> optionDto.toModel() })
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}