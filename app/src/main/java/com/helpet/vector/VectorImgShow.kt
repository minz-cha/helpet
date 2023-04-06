package com.helpet.vector

import com.helpet.R
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_vector_imageshow.*


class VectorImgShow :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_vector_imageshow)

        val intent = intent
        val bitmap = intent.getParcelableExtra<Bitmap>("사진")
        vector_img.setImageBitmap(bitmap)
    }
}