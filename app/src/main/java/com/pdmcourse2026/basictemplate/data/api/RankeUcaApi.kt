package com.pdmcourse2026.basictemplate.data.api

import com.pdmcourse2026.basictemplate.data.api.dto.CreateMassVoteRequestDto
import com.pdmcourse2026.basictemplate.data.api.dto.OptionDto
import com.pdmcourse2026.basictemplate.data.api.dto.QuestionDto
import com.pdmcourse2026.basictemplate.data.api.dto.QuestionsDto
import com.pdmcourse2026.basictemplate.data.api.dto.VoteDto
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class CreateQuestionRequest(val text: String)

@Serializable
data class UpdateQuestionRequest(val text: String)

@Serializable
data class CreateOptionRequest(val questionId: Int, val value: String)

@Serializable
data class UpdateOptionRequest(val value: String)

class RankeUcaApi {
    private val client = KtorClient.client

    suspend fun getQuestions(): List<QuestionDto> {
        return client.get("questions").body()
    }

    suspend fun createQuestion(text: String) {
        client.post("questions") {
            contentType(ContentType.Application.Json)
            setBody(CreateQuestionRequest(text))
        }
    }

    suspend fun updateQuestion(id: Int, text: String) {
        client.put("questions/$id") {
            contentType(ContentType.Application.Json)
            setBody(UpdateQuestionRequest(text))
        }
    }

    suspend fun deleteQuestion(id: Int) {
        client.delete("questions/$id")
    }

    suspend fun getOptions(): List<OptionDto> {
        return client.get("options").body()
    }

    suspend fun createOption(questionId: Int, value: String) {
        client.post("options") {
            contentType(ContentType.Application.Json)
            setBody(CreateOptionRequest(questionId, value))
        }
    }

    suspend fun updateOption(id: Int, value: String) {
        client.put("options/$id") {
            contentType(ContentType.Application.Json)
            setBody(UpdateOptionRequest(value))
        }
    }

    suspend fun deleteOption(id: Int) {
        client.delete("options/$id")
    }

    suspend fun getQuestionsP3(): List<QuestionsDto> {
        return client.get("parcialtres/questions").body()
    }

    suspend fun postQuestionsP3(votes: List<VoteDto>) {
        client.post("parcialtres/votes") {
            contentType(ContentType.Application.Json)
            setBody(CreateMassVoteRequestDto(votes))
        }
    }
}
