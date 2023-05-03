package com.helpet.vector

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.helpet.R
import com.helpet.databinding.ActivityVectorCameraBinding
import kotlinx.android.synthetic.main.activity_pet_register.*
import kotlinx.android.synthetic.main.activity_vector_camera.*
import kotlinx.android.synthetic.main.activity_vector_choice_pet.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class PetRegisterActivity : BaseActivity() {
    override fun permissionGranted(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun permissionDenied(requestCode: Int) {
        TODO("Not yet implemented")
    }

}



