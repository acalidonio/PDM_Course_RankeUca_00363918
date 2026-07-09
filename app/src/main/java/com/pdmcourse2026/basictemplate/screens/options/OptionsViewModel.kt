package com.pdmcourse2026.basictemplate.screens.options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdmcourse2026.basictemplate.RankeUcaApplication
import com.pdmcourse2026.basictemplate.data.repository.QuestionOfflineFirstRepository
import com.pdmcourse2026.basictemplate.model.Option
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OptionsViewModel(
    private val repository: QuestionOfflineFirstRepository,
    private val questionId: Int
) : ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    val options: StateFlow<List<Option>> =
        repository.getOptions(questionId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                repository.refresh()
            } catch (e: Exception) {
                if (options.value.isEmpty()) {
                    _error.value = "Sin conexión y sin datos en caché"
                }
            }
        }
    }

    fun addOption(value: String, imageUrl: String?) {
        viewModelScope.launch {
            try {
                repository.createOption(questionId, value)
            } catch (e: Exception) {
                _error.value = "Se necesita conexión a internet para crear opciones"
            }
        }
    }

    fun deleteOption(option: Option) {
        viewModelScope.launch {
            try {
                repository.deleteOption(option.id)
            } catch (e: Exception) {
                _error.value = "Se necesita conexión a internet para borrar opciones"
            }
        }
    }

    fun updateOption(option: Option) {
        viewModelScope.launch {
            try {
                repository.updateOption(option.id, option.value)
            } catch (e: Exception) {
                _error.value = "Se necesita conexión a internet para editar opciones"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    companion object {
        fun provideFactory(questionId: Int) = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as RankeUcaApplication
                OptionsViewModel(app.appProvider.provideRepository(), questionId)
            }
        }
    }
}