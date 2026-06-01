package com.pdmcourse2026.basictemplate.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes : NavKey {
  @Serializable
  data object Home : Routes()
  @Serializable
  data object Results : Routes()
}