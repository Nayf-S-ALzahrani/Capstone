package com.example.capstone.utils

import android.os.Looper
import android.os.Handler

class Timer(listener: OnTimerTickListener) {

    interface OnTimerTickListener {
        fun onTimerTick(duration: String)
    }

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    private var duration = 0L
    private var delay = 100L

    init {
        runnable = Runnable {
            duration += delay
            handler.postDelayed(runnable, delay)
            listener.onTimerTick(format())
        }
    }

    fun start() {
        handler.postDelayed(runnable, delay)
    }

    fun pause() {
        handler.removeCallbacks(runnable)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
        duration = 0L
    }

    private fun format(): String {
        val second = (duration / 1000) % 60
        val minutes = (duration / (1000 * 60)) % 60
        val hours = (duration / (1000 * 60 * 60)) % 60

        val formatted = if (hours > 0) "%02d:%02d:%02d".format(hours, minutes, second)
        else "%02d:%02d".format(minutes, second)

        return formatted
    }
}