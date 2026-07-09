package com.pdmcourse2026.basictemplate.data

import android.content.Context
import com.pdmcourse2026.basictemplate.data.database.AppDatabase
import com.pdmcourse2026.basictemplate.data.repository.QuestionOfflineFirstRepository
import com.pdmcourse2026.basictemplate.data.repository.impl.QuestionOfflineFirstRepositoryImpl

class AppProvider(context: Context) {

    private val appDatabase = AppDatabase.getDatabase(context)
    private val optionDao = appDatabase.optionDao()
    private val questionDao = appDatabase.questionDao()

    private val offlineFirstRepository: QuestionOfflineFirstRepository = QuestionOfflineFirstRepositoryImpl(questionDao, optionDao)

    fun provideRepository(): QuestionOfflineFirstRepository {
        return offlineFirstRepository
    }
}