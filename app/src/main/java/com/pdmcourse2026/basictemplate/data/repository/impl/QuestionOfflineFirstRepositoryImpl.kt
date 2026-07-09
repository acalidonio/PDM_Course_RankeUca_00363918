package com.pdmcourse2026.basictemplate.data.repository.impl

import com.pdmcourse2026.basictemplate.data.api.RankeUcaApi
import com.pdmcourse2026.basictemplate.data.api.dto.toEntity
import com.pdmcourse2026.basictemplate.data.database.dao.OptionDao
import com.pdmcourse2026.basictemplate.data.database.dao.QuestionDao
import com.pdmcourse2026.basictemplate.data.database.entities.OptionEntity
import com.pdmcourse2026.basictemplate.data.database.entities.QuestionEntity
import com.pdmcourse2026.basictemplate.data.database.entities.toModel
import com.pdmcourse2026.basictemplate.data.repository.QuestionOfflineFirstRepository
import com.pdmcourse2026.basictemplate.model.Option
import com.pdmcourse2026.basictemplate.model.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuestionOfflineFirstRepositoryImpl(
    private val questionDao: QuestionDao,
    private val optionDao: OptionDao,
    private val api: RankeUcaApi = RankeUcaApi()
) : QuestionOfflineFirstRepository {

    override fun getQuestions(): Flow<List<Question>> {
        return questionDao.getQuestionsWithOptions().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun getOptions(questionId: Int): Flow<List<Option>> {
        return optionDao.getOptionsForQuestion(questionId).map { entities ->
            entities.map { it.toModel() }
        }
    }

    override suspend fun refresh() {
        val questionsDto = api.getQuestions()
        val optionsDto = api.getOptions()

        questionDao.upsertAll(questionsDto.map { it.toEntity() })

        optionDao.upsertAll(optionsDto.map { it.toEntity(it.questionId ?: 0) })
    }

    override suspend fun createQuestion(text: String) {
        api.createQuestion(text)
        refresh()
    }

    override suspend fun updateQuestion(id: Int, text: String) {
        api.updateQuestion(id, text)
        refresh()
    }

    override suspend fun deleteQuestion(id: Int) {
        api.deleteQuestion(id)
        refresh()
        questionDao.deleteQuestion(QuestionEntity(id, ""))
    }

    override suspend fun createOption(questionId: Int, value: String) {
        api.createOption(questionId, value)
        refresh()
    }

    override suspend fun updateOption(id: Int, value: String) {
        api.updateOption(id, value)
        refresh()
    }

    override suspend fun deleteOption(id: Int) {
        api.deleteOption(id)
        optionDao.deleteOption(OptionEntity(id = id, value = "", imageUrl = null, questionId = 0))
        refresh()
    }
}
