package com.example.tofuapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SPLASH_TIME_DURATION = 1500L
class LauncherViewModel: ViewModel() {
    private val navigateState = MutableLiveData(false)
    fun getNavigateState(): LiveData<Boolean> = navigateState

    fun startTimer(){
        viewModelScope.launch {
            delay(SPLASH_TIME_DURATION)
            navigateState.postValue(true)
        }
    }
}