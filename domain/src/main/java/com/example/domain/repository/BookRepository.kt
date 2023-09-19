package com.example.domain.repository

import com.example.domain.model.BookDisease


interface BookRepository{
    fun getAllDisease(): List<BookDisease>

    fun getDiseaseInformation(checkedStr : List<String>) : List<BookDisease>
}