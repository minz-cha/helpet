package com.example.data.source.local

import android.content.Context
import com.example.data.mapper.mapToBDisease
import com.example.data.model.local.Disease
import com.example.domain.model.BookDisease
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class bookLocalSourceImpl @Inject constructor(@ApplicationContext private val context: Context) : bookLocalSource {
    private val dbHelper: dbHelper = dbHelper(context)

    override fun getAllDisease(): List<BookDisease> {
        val allDiseases = dbHelper.getAllDiseases()
        val map1 = allDiseases.map { Disease(it.name, it.symptoms, it.causes, it.treatments) }
        val maptoBook  = mapToBDisease(map1)

        return maptoBook
    }

    override fun getDiseaseInformation(checkedStr : List<String>): List<BookDisease> {
        val specificDisease = dbHelper.getDiseaseInformation(checkedStr)
        val map = specificDisease.map { Disease(it.name, it.symptoms, it.causes, it.treatments) }
        val maptoBook  = mapToBDisease(map)

        return maptoBook
    }


}