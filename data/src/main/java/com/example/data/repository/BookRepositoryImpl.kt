package com.example.data.repository

import com.example.data.model.local.Disease
import com.example.data.source.local.bookLocalSource
import com.example.data.source.local.dbHelper
import com.example.domain.model.BookDisease
import com.example.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookLocalSource : bookLocalSource) : BookRepository {
    override fun getAllDisease(): List<BookDisease> {
        return bookLocalSource.getAllDisease()
    }

    override fun getDiseaseInformation(checkedStr : List<String>): List<BookDisease> {
        return bookLocalSource.getDiseaseInformation(checkedStr)
    }

}