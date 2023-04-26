package com.helpet.vector


import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.util.*


abstract class BaseActivity:AppCompatActivity() {
    abstract fun permissionGranted(requestCode: Int)
    abstract fun permissionDenied(requestCode:Int)

    //권한 검사
    fun requirePermissions(permissions:Array<String>,requestCode: Int){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            permissionGranted(requestCode)
        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions,requestCode)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (grantResults.all{it==PackageManager.PERMISSION_GRANTED}){
            permissionGranted(requestCode)
        }
        else{
            permissionDenied(requestCode)
        }
    }



}