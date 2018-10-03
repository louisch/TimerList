package xyz.louischan.timerlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


class MainActivityViewModel : ViewModel() {

    private val _timersList = mutableListOf<Timer>()
    private val _timers: MutableLiveData<List<Timer>> by lazy {
        MutableLiveData<List<Timer>>()
    }
    val timers: LiveData<List<Timer>> = _timers

    fun addNewTimer() {
        _timersList.add(Timer())
        _timers.value = _timersList
    }
}