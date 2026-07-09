package com.pdmcourse2026.basictemplate.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.pdmcourse2026.basictemplate.RankeUcaApplication
import com.pdmcourse2026.basictemplate.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    val isLoggedIn: StateFlow<Boolean?> = repository.isLoggedIn
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val userName: StateFlow<String?> = repository.userName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _error.value = null
            _isLoading.value = true
            try {
                repository.login(email, password)
            } catch (e: Exception) {
                _error.value = "Correo o contraseña incorrectos"
            }
            _isLoading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch { repository.logout() }
    }
    
    fun clearError() {
        _error.value = null
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as RankeUcaApplication
                return AuthViewModel(
                    application.appProvider.provideAuthRepository()
                ) as T
            }
        }
    }
}
