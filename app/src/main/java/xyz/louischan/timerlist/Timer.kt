package xyz.louischan.timerlist

import java.util.*


class Timer(val timestamp: Date) {

    val timer = java.util.Timer()

    constructor() : this(Calendar.getInstance().time)

    fun runningTime(currentTime: Date): String {
        return formatSeconds(runningTimeLong(currentTime))
    }

    fun runningTimeLong(currentTime: Date): Long {
        return currentTime.time - timestamp.time
    }

    fun formatSeconds(totalMilliseconds: Long): String {
        val totalSeconds = totalMilliseconds / 1000
        val hours = totalSeconds.div(3600)
        val minutes = totalSeconds.div(60).rem(60)
        val seconds = totalSeconds.rem(60)
        return "%02d:%02d:%02d".format(hours, minutes, seconds)
    }
}
