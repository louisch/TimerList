package xyz.louischan.timerlist

import android.arch.lifecycle.*


class MainActivityViewModel : ViewModel() {

    private val _timersList = mutableListOf<Timer>()
    private val _timers: MutableLiveData<List<Timer>> by lazy {
        MutableLiveData<List<Timer>>()
    }
    val timers: LiveData<List<Timer>> = _timers

    fun addNewTimer(): List<Timer> {
        val timer = Timer()
        _timersList.add(timer)
        _timers.value = _timersList
        return _timersList
    }
}