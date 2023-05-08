package com.helpet.vector

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.Base64.getDecoder
import java.util.Base64.getEncoder

class SerialBitmap {
    companion object {
        fun translate(bitmap: Bitmap) : ByteArray {
            lateinit var bitmapData : ByteArray
            try {
                var stream: ByteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                bitmapData = stream.toByteArray()
                stream.flush()
                stream.close()
            } catch (e : Exception) {
                e.printStackTrace()
            }
            return bitmapData
        }
        fun translate(bitmapData : ByteArray) : Bitmap {
            return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.size).copy(Bitmap.Config.ARGB_8888, true)
        }
    }

}
