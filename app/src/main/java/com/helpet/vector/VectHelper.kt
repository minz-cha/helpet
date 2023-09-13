package com.helpet.vector

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class VectHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NAME = "VectDatabase.db"
        private const val TABLE_VECTS = "vects"
        private const val COLUMN_ID = "id"
        private const val COLUMN_PET_NAME = "pet_name"
        private const val COLUMN_PET_AGE = "pet_age"
        private const val COLUMN_PET_BIRTH = "pet_birth"
        private const val COLUMN_VECT_IMG = "vect_img"
        private const val COLUMN_VECT_NAME = "vect_name"
        private const val COLUMN_VECT_DATE = "vect_date"
        private const val COLUMN_VECT_PROB = "vect_prob"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_VECTS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_PET_NAME TEXT, $COLUMN_PET_AGE INTEGER, $COLUMN_PET_BIRTH TEXT, "
                + "$COLUMN_VECT_IMG BLOB, $COLUMN_VECT_NAME TEXT, $COLUMN_VECT_DATE TEXT, $COLUMN_VECT_PROB REAL)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("oldversion", oldVersion.toString())
        Log.d("newversion", newVersion.toString())

        // 예를 들어, oldVersion이 1이고 newVersion이 2일 때만 실행
        if (oldVersion == 4 && newVersion == 5) {
            // 이전 버전의 테이블을 삭제
            db.execSQL("DROP TABLE IF EXISTS $TABLE_VECTS")

            // 새로운 스키마로 테이블 생성
            val createTableQuery = ("CREATE TABLE $TABLE_VECTS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "$COLUMN_PET_NAME TEXT, $COLUMN_PET_AGE INTEGER, $COLUMN_PET_BIRTH TEXT, "
                    + "$COLUMN_VECT_IMG BLOB, $COLUMN_VECT_NAME TEXT, $COLUMN_VECT_DATE TEXT, $COLUMN_VECT_PROB REAL)")
            db.execSQL(createTableQuery)
        } else {
            // 다른 경우에는 필요한 업그레이드 로직을 수행
            // 예: 다른 버전 간의 업그레이드 처리
        }
    }
    fun insertVectData(
        petName: String,
        petAge: Int,
        petBirth: String,
        vectImg: ByteArray, // 이미지 데이터를 Base64 형식의 문자열로 저장
        vectName: String,
        vectDate: String,
        vectProb: Double
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_PET_NAME, petName)
        values.put(COLUMN_PET_AGE, petAge)
        values.put(COLUMN_PET_BIRTH, petBirth)
        values.put(COLUMN_VECT_IMG, vectImg)
        values.put(COLUMN_VECT_NAME, vectName)
        values.put(COLUMN_VECT_DATE, vectDate)
        values.put(COLUMN_VECT_PROB, vectProb)

        val success = db.insert(TABLE_VECTS, null, values)
        db.close()
        return success != -1L
    }

    fun getVectorInfo(petage: Int, petbirth: String, petName: String, vectDate: String, vectProb: Double): VectInfo? {
        val db = this.readableDatabase
        var vectorInfo: VectInfo? = null

        // 실제 데이터베이스 쿼리를 수행하여 데이터를 가져오는 코드
        val query = "SELECT $COLUMN_VECT_IMG, $COLUMN_VECT_NAME FROM $TABLE_VECTS " +
                "WHERE $COLUMN_PET_AGE = ? AND $COLUMN_PET_BIRTH = ? AND $COLUMN_PET_NAME = ? AND $COLUMN_VECT_DATE = ? AND $COLUMN_VECT_PROB = ?"

        val selectionArgs = arrayOf(petage.toString(), petbirth, petName, vectDate, vectProb.toString())

        val cursor = db.rawQuery(query, selectionArgs)
        if (cursor.moveToFirst()) {
            val vectImg = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_VECT_IMG))
            val vectnameString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VECT_NAME))
            val vectnameList = vectnameString.split(", ") // 쉼표로 구분된 진단명을 리스트로 변환

            vectorInfo = VectInfo(vectImg, vectnameList)
        }
        cursor.close()
        db.close()

        return vectorInfo
    }

    fun getAllVectorInfo(): List<VectInfoes> {
        val db = this.readableDatabase
        val vectorInfoList = mutableListOf<VectInfoes>()

        // 실제 데이터베이스 쿼리를 수행하여 데이터를 가져오는 코드
        val query = "SELECT * FROM $TABLE_VECTS"

        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val vectImg = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_VECT_IMG))
            val vectnameString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VECT_NAME))
            val vectnameList = vectnameString.split(", ") // 쉼표로 구분된 진단명을 리스트로 변환

            val petName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_NAME))
            val petAge = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_AGE))
            val petBirth = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_BIRTH))
            val vectDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VECT_DATE))
            val vectProb = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VECT_PROB))

            val vectorInfo = VectInfoes( vectnameList, petName, petAge, petBirth, vectDate, vectProb)
            vectorInfoList.add(vectorInfo)
        }
        cursor.close()
        db.close()

        return vectorInfoList
    }

    fun getDistinctPetNames(): List<String> {
        val db = this.readableDatabase
        val petNames = mutableListOf<String>()

        // 실제 데이터베이스 쿼리를 수행하여 고유한 pet_name 값을 가져오는 코드
        val query = "SELECT DISTINCT $COLUMN_PET_NAME FROM $TABLE_VECTS"

        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val petName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_NAME))
            petNames.add(petName)
        }
        cursor.close()
        db.close()

        return petNames
    }
}

data class VectInfo(
    val vectImg: ByteArray, // 이미지 데이터를 Base64 형식의 문자열로 저장
    val vectname: List<String> // 진단명 리스트
)

data class VectInfoes(
   // 이미지 데이터를 Base64 형식의 문자열로 저장
    val vectname: List<String>, // 진단명 리스트
    val petName: String,
    val petAge: Int,
    val petBirth: String,
    val vectDate: String,
    val vectProb: Double
)