package com.pdmcourse2026.basictemplate.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetOptionsResponseDto (
    val list : List<OptionDto>
)