package com.example.userapplication.firstScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val name = MutableLiveData<String>()

    fun setName(name: String) {
        this.name.value = name
    }
}

