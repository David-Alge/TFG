package com.example.tfg

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel: ViewModel() {
    var string = MutableLiveData<String>()
}