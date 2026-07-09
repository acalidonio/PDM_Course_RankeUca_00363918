package com.pdmcourse2026.basictemplate.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes : NavKey {
  @Serializable
  data object Menu : Routes()
  @Serializable
  data object Home : Routes()
  @Serializable
  data object Results : Routes()
  @Serializable
  data class AdminOptions(val questionId: Int) : Routes()
  @Serializable
  data object AdminQuestions : Routes()
  @Serializable
  data object MassVote : Routes()
}
