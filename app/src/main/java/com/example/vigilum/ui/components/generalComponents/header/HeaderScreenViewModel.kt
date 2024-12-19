package com.example.adsnetkyc.presentation.qouikscreens.common.header

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HeaderScreenViewModel @Inject constructor() : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _dateTime = MutableStateFlow(LocalDateTime.now())
    val dateTime: StateFlow<LocalDateTime> @RequiresApi(Build.VERSION_CODES.O)
    get() = _dateTime

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000) // Update every second
                _dateTime.value = LocalDateTime.now()
            }
        }
    }
}