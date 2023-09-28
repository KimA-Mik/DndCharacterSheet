package ru.kima.dndcharactersheet.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable

class GraphicUtils {
    companion object {
        fun rotateDrawable(drawable: Drawable, degrees: Float, resources: Resources): Drawable {
            val bmpOriginal = drawable.toBitmap()
            val bmpResult =
                Bitmap.createBitmap(bmpOriginal.height, bmpOriginal.width, Bitmap.Config.ARGB_8888)
            val tempCanvas = Canvas(bmpResult)
            val pivot = bmpOriginal.height / 2
            tempCanvas.rotate(degrees, pivot.toFloat(), pivot.toFloat())
            tempCanvas.drawBitmap(bmpOriginal, 0f, 0f, null)
            return bmpResult.toDrawable(resources)
        }
    }
}