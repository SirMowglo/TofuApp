package com.example.tofuapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {
    private var mJob: Job? = null

    protected fun <T> baseRequest(
        liveData: MutableLiveData<T>,
        errorHandler: CoroutineErrorHandler,
        request: () -> Flow<T>
    ) {
        mJob = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler {_,error ->
            viewModelScope.launch(Dispatchers.Main){
                errorHandler.onError(error.localizedMessage ?: "An error occured! Please try again")
            }
        }) {
            request().collect{
                withContext(Dispatchers.Main){
                    liveData.value = it
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mJob?.let {
            if (it.isActive){
                it.cancel()
            }
        }
    }
}

interface CoroutineErrorHandler{
    fun onError(message:String)
}