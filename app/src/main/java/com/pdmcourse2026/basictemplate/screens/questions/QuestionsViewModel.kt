package com.pdmcourse2026.basictemplate.screens.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.pdmcourse2026.basictemplate.RankeUcaApplication
import com.pdmcourse2026.basictemplate.data.repository.QuestionOfflineFirstRepository
import com.pdmcourse2026.basictemplate.model.Question
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuestionsViewModel(
    private val repository: QuestionOfflineFirstRepository
) : ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    val questions: StateFlow<List<Question>> = repository.getQuestions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
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
                if (questions.value.isEmpty()) {
                    _error.value = "Sin conexión y sin datos en caché"
                }
            }
        }
    }

    fun addQuestion(title: String) {
        viewModelScope.launch {
            try {
                repository.createQuestion(title)
            } catch (e: Exception) {
                _error.value = "Se necesita conexión a internet para crear preguntas"
            }
        }
    }

    fun deleteQuestion(question: Question) {
        viewModelScope.launch {
            try {
                repository.deleteQuestion(question.id)
            } catch (e: Exception) {
                _error.value = "Se necesita conexión a internet para borrar preguntas"
            }
        }
    }

    fun updateQuestion(question: Question) {
        viewModelScope.launch {
            try {
                repository.updateQuestion(question.id, question.title)
            } catch (e: Exception) {
                _error.value = "Se necesita conexión a internet para editar preguntas"
            }
        }
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
                val application = checkNotNull(extras[APPLICATION_KEY]) as RankeUcaApplication
                return QuestionsViewModel(
                    application.appProvider.provideRepository()
                ) as T
            }
        }
    }
}
