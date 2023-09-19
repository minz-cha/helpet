package com.example.domain.usecase

import com.example.domain.model.BookDisease
import com.example.domain.repository.BookRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class BookUseCase @Inject constructor(private val bookRepository : BookRepository){
    fun getAllDisease() : List<BookDisease> {
        return bookRepository.getAllDisease()
    }

    fun getDiseaseInformation(checkedStr : List<String>): List<BookDisease>{
        return bookRepository.getDiseaseInformation(checkedStr)
    }
}