package com.example.data.source.local

import com.example.data.model.local.Disease
import com.example.domain.model.BookDisease


interface bookLocalSource {
    fun getAllDisease() : List<BookDisease>

    fun getDiseaseInformation(checkedStr : List<String>) : List<BookDisease>

}