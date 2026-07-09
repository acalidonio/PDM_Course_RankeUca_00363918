package com.pdmcourse2026.basictemplate.screens.massvote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdmcourse2026.basictemplate.data.repository.impl.QuestionOfflineFirstRepositoryImpl
import com.pdmcourse2026.basictemplate.model.Vote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MassVoteViewModel : ViewModel() {

    private val repository: QuestionOfflineFirstRepositoryImpl = QuestionOfflineFirstRepositoryImpl()

    private val _votes = MutableStateFlow<List<Vote>>(emptyList())
    val votes = _votes.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _refresh = MutableStateFlow(false)
    val refresh = _refresh.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _votedOptionId = MutableStateFlow<Int?>(null)
    val votedOptionId = _votedOptionId.asStateFlow()

init {
        loadOptions()
    }


    fun loadOptions() {
        viewModelScope.launch {
            _error.value = null
            _loading.value = true

            repository.getQuestions()
                .onSuccess { questions -> _questions.value = questions }
                .onFailure { _ -> _error.value = getErrorMessage() }

            _loading.value = false
        }
    }

    fun refreshResults() {
        viewModelScope.launch {
            _error.value = null
            _refresh.value = true

            repository.getOptions()
                .onSuccess { options ->
                    _options.value = options.sortedByDescending { it.votes }
                }
                .onFailure { _ -> _error.value = getErrorMessage() }

            _refresh.value = false
        }
    }

    fun getErrorMessage(): String {
        return "Hubo un error. Presiona el botón para recargar."
    }

    fun resetSelection() {
        _votedOptionId.value = null
    }

    fun selectItem() {
        if (_loading.value || _votedOptionId.value != null) return

        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.vote(id)
                .onSuccess {
                    _votedOptionId.value = id
                }
                .onFailure { _ ->
                    _error.value = getErrorMessage()
                }

            _loading.value = false
        }
    }
}
