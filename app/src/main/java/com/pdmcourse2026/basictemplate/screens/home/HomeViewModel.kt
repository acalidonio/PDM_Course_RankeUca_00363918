package com.pdmcourse2026.basictemplate.screens.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdmcourse2026.basictemplate.data.repository.Repository
import com.pdmcourse2026.basictemplate.data.repository.impl.RepositoryImpl
import com.pdmcourse2026.basictemplate.model.Option
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {
    private val postRepository: Repository = RepositoryImpl()
    private val _options = MutableStateFlow<List<Option>>(emptyList())
    val options = _options.asStateFlow()

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

            postRepository.getOptions()
                .onSuccess { options -> _options.value = options }
                .onFailure { _ -> _error.value = getErrorMessage() }

            _loading.value = false
        }
    }

    fun refreshOptions() {
        viewModelScope.launch {
            _error.value = null
            _refresh.value = true

            postRepository.getOptions()
                .onSuccess { options -> _options.value = options }
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

    fun selectItem(id: Int) {
        if (_loading.value || _votedOptionId.value != null) return
        
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            
            postRepository.vote(id)
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