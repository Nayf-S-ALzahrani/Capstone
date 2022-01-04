package com.example.capstone.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.capstone.R

private const val TAG = "WaveformView"
class WaveformView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paint = Paint()
    private var amplitudes = ArrayList<Float>()
    private var spikes = ArrayList<RectF>()

    private var radius = 3f
    private var spikeWidth = 9f
    private var spaceBetween = 6f

    private var screenWidth = 0f
    private var screenHeight = 400f

    private var maxSpikes = 0
    init {
        paint.color = resources.getColor(R.color.black)
        screenWidth = resources.displayMetrics.widthPixels.toFloat()

        maxSpikes = (screenWidth / (spikeWidth+spaceBetween)).toInt()
    }

    fun addAmplitude(amp: Float){
        var norm = Math.min(amp.toInt()/7, 400).toFloat()
        amplitudes.add(norm)

        spikes.clear()
        var amps = amplitudes.takeLast(maxSpikes)
        for (i in amps.indices){
            var left = screenWidth -  i * (spikeWidth + spaceBetween)
            var top = screenHeight/2 - amps[i]/2
            var right = left + spikeWidth
            var bottom = top + amps[i]
//            Log.d(TAG, "addAmplitude: ${bottom}")
            spikes.add(RectF(left, top, right, bottom))
        }

        invalidate()
    }

    fun clear(): ArrayList<Float>{
        var amps = amplitudes.clone() as ArrayList<Float>
        amplitudes.clear()
        spikes.clear()
        invalidate()

        return amps
    }
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        spikes.forEach{
            Log.d(TAG, "draw: $it $radius $radius $paint")
            canvas?.drawRoundRect(it, radius, radius, paint)
        }
    }

}