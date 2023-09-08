package com.helpet.vector

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PetHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "PetDatabase.db"
        private const val TABLE_PETS = "pets"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_AGE = "age"
        private const val COLUMN_BIRTH = "birth"
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_PETS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, $COLUMN_AGE INTEGER, $COLUMN_BIRTH TEXT, "
                + "$COLUMN_GENDER TEXT, $COLUMN_IMAGE BLOB)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PETS")
        onCreate(db)
    }

    fun insertPetData(
        name: String,
        age: Int,
        birth: String,
        gender: String,
        image: ByteArray
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_AGE, age)
        values.put(COLUMN_BIRTH, birth)
        values.put(COLUMN_GENDER, gender)
        values.put(COLUMN_IMAGE, image)

        val success = db.insert(TABLE_PETS, null, values)
        db.close()
        return success != -1L
    }

    fun getAllPets(): ArrayList<PetModel> {
        val petList = ArrayList<PetModel>()
        val selectQuery = "SELECT * FROM $TABLE_PETS"
        val db = this.readableDatabase

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val pet = PetModel(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                        cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
                    )
                    petList.add(pet)
                } while (cursor.moveToNext())
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            return ArrayList()
        } finally {
            cursor?.close()
            db.close()
        }
        return petList
    }

    fun getPetImage(name: String, birth: String, gender: String, age: Int): ByteArray? {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_IMAGE FROM $TABLE_PETS WHERE $COLUMN_NAME = ? AND $COLUMN_BIRTH = ? AND $COLUMN_GENDER = ? AND $COLUMN_AGE = ?"
        val selectionArgs = arrayOf(name, birth, gender, age.toString())
        val cursor = db.rawQuery(query, selectionArgs)

        if (cursor.moveToFirst()) {
            val imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
            cursor.close()
            return imageByteArray
        }

        cursor.close()
        return null
    }
}

data class PetModel(
    val name: String,
    val age: Int,
    val birth: String,
    val gender: String,
    val image: ByteArray
)