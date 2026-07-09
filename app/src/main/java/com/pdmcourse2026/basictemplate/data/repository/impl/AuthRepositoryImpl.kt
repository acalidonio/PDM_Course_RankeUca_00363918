package com.pdmcourse2026.basictemplate.data.repository.impl

import com.pdmcourse2026.basictemplate.data.api.KtorClient
import com.pdmcourse2026.basictemplate.data.api.RankeUcaApi
import com.pdmcourse2026.basictemplate.data.api.dto.LoginRequestDto
import com.pdmcourse2026.basictemplate.data.repository.AuthRepository
import com.pdmcourse2026.basictemplate.data.session.SessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val session: SessionManager,
    private val api: RankeUcaApi = RankeUcaApi()
) : AuthRepository {
    override val isLoggedIn: Flow<Boolean> = session.token.map { it != null }
    override val userName: Flow<String?> = session.userName

    override suspend fun login(email: String, password: String) {
        val response = api.login(LoginRequestDto(email, password))
        session.save(response.token, response.user.name)
        KtorClient.authToken = response.token
    }

    override suspend fun logout() {
        session.clear()
        KtorClient.authToken = null
    }
}
