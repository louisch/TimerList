package xyz.louischan.timerlist

import java.util.*

class Timer(val timestamp: Date) {

    constructor() : this(Calendar.getInstance().time)

    fun runningTime(currentTime: Date): String {
        return formatSeconds(currentTime.time - timestamp.time)
    }

    fun formatSeconds(totalSeconds: Long): String {
        val hours = totalSeconds.div(3600)
        val minutes = totalSeconds.div(60).rem(60)
        val seconds = totalSeconds.rem(60)
        return "%2d:%2d:%2d".format(hours, minutes, seconds)
    }
}
