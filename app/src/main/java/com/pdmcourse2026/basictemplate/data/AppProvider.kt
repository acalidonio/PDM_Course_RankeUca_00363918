package com.pdmcourse2026.basictemplate.data

import android.content.Context
import com.pdmcourse2026.basictemplate.data.database.AppDatabase
import com.pdmcourse2026.basictemplate.data.repository.QuestionOfflineFirstRepository
import com.pdmcourse2026.basictemplate.data.repository.impl.QuestionOfflineFirstRepositoryImpl
import com.pdmcourse2026.basictemplate.data.session.SessionManager
import com.pdmcourse2026.basictemplate.data.repository.AuthRepository
import com.pdmcourse2026.basictemplate.data.repository.impl.AuthRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.pdmcourse2026.basictemplate.data.api.KtorClient

class AppProvider(context: Context) {

    private val appDatabase = AppDatabase.getDatabase(context)
    private val optionDao = appDatabase.optionDao()
    private val questionDao = appDatabase.questionDao()

    private val sessionManager = SessionManager(context)
    private val authRepository: AuthRepository = AuthRepositoryImpl(sessionManager)

    init {
        // Initialize Ktor token synchronously for early requests
        runBlocking {
            KtorClient.authToken = sessionManager.token.first()
        }
    }

    private val offlineFirstRepository: QuestionOfflineFirstRepository = QuestionOfflineFirstRepositoryImpl(questionDao, optionDao)

    fun provideRepository(): QuestionOfflineFirstRepository {
        return offlineFirstRepository
    }

    fun provideAuthRepository(): AuthRepository {
        return authRepository
    }
}