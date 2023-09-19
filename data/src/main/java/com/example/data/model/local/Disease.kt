package com.example.data.model.local

import com.example.data.source.local.dbHelper

data class Disease(
    val name: String,
    val symptoms: String,
    val cause: String,
    val treats: String
) {
    companion object {
        fun dbHelper.Disease.toDisease(): Disease {
            return Disease(this.name, this.symptoms, this.causes, this.treatments)
        }
    }
}
