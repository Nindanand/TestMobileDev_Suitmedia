package com.example.userapplication.firstScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    fun setName(name: String) {
        _name.value = name
        Log.d("SharedViewModel", "Name set: $name")
    }

    fun getName(): String? {
        return _name.value
    }
}

