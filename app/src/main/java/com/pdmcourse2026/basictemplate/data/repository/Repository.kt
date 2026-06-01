package com.pdmcourse2026.basictemplate.data.repository

import com.pdmcourse2026.basictemplate.model.Error
import com.pdmcourse2026.basictemplate.model.Option

interface Repository {
    suspend fun vote(body: Int): Result<Error>
    suspend fun getOptions(): Result<List<Option>>
}