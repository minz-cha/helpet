package com.example.data.mapper

import com.example.data.model.local.Disease
import com.example.domain.model.BookDisease

fun mapToBDisease(diseases: List<Disease>): List<BookDisease> {
    return diseases.map { disease ->
        BookDisease(disease.name, disease.symptoms, disease.cause, disease.treats)
    }
}