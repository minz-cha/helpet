package com.helpet.books

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "disease.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_DISEASES = "diseases"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_SPECIES = "species"
        private const val COLUMN_SYMPTOMS = "symptoms"
        private const val COLUMN_CAUSES = "causes"
        private const val COLUMN_TREATMENTS = "treatments"

        private const val CREATE_TABLE_DISEASES = "CREATE TABLE $TABLE_DISEASES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_SPECIES TEXT, " +
                "$COLUMN_SYMPTOMS TEXT, " +
                "$COLUMN_CAUSES TEXT, " +
                "$COLUMN_TREATMENTS TEXT" +
                ")"
    }

    override fun onCreate(db: SQLiteDatabase) {

        Log.d("oncreate", "Oncreate")
        db.execSQL(CREATE_TABLE_DISEASES)
        // 초기 데이터 삽입
        insertDisease(db, "결막염", "강아지", "결막염에 걸리면 눈꺼풀 주위가 아프거나 가려워진다. 개는 이에 앞발로 눈을 비비거나 바닥에 얼굴을 문지르는 등의 행동을 보인다. 이때 눈꺼풀을 깨끗한 손으로 들어올려보면 눈 흰자가 붉게 충혈되고 일부는 부어오를 수 있다. 눈물이 많이 나기도 하며 눈곱이 많이 낄 수 있다.", "크게 세 가지로, 1) 눈을 세게 문지르거나, 눈에 털이 들어가는 등의물리 & 샴푸나 약품 등의 화학적 자극 2) 세균 또는 바이러스 감염 3) 알레르겐(알레르기 원인 물질)이 눈에 직접 닿았거나, 코로 흡입, 입으로 섭취했을 경우 알레르기 반응이 원인이 된다.", "물리·화학적 원인의 결막염을 예방하려면 눈썹 등 눈 부위의 털 관리를 세심히 하며, 샴푸가 눈 부위에 닿지 않도록 유의한다. 감염성 결막염을 예방하려면 청결한 몸 상태를 유지하고, 반려견이 지내는 집안 환경 역시 깔끔하게 자주 청소해준다. 알레르기성 결막염 예방을 위해서는 반려견의 알레르겐이 무엇인지를 미리 알아두고, 가급적 정해진 식사만 하도록 한다.")
        insertDisease(db, "백내장", "강아지", "초기에는 육안으로 백내장 여부를 인지하기 어렵다. 증상이 심해질수록 시력 결손이 발생하고, 소실까지도 이르며 수정체가 수축해 수정체에 주름이 생기며, 눈 안에 염증이 발생할 수도 있다.", "백내장을 유발하는 원인은 다양한데, 개에서 가장 흔한 원인은 유전이다. 이 외에도 다른 안과 질환이나 당뇨로 인한 혈당 수치 상승 등이 백내장을 유발할 수 있고, 노화 역시 영향을 미칠 수 있다.", "백내장은 유전적인 영향이 강하다. 내 반려견의 부모가 과거 백내장을 앓았던 경험이 있다면 조금 더 주의를 기울여 눈을 살펴볼 수 있다. 유전성 백내장은 비교적 어린 나이에도 발생하기 시작하므로, 안과 검진을 통해 백내장 여부를 확인하고 주기적으로 검진을 함으로써 백내장의 진행 여부를 파악하고 제 때 필요한 치료를 하는 것이 중요하다.")
        insertDisease(db, "각막궤양", "강아지", "각막에는 많은 신경이 분포돼 있어 염증이 생기면 큰 통증을 느낀다. 따라서 개는 눈을 계속 감고 있거나 깜빡거릴 수 있다. 혹은 앞발로 눈을 문지르고 있어 한눈에 봐도 눈에 이상이 있다고 짐작 가능하다. 통증이 더 심해지면 얼굴을 바닥에 문지르며 아파하고, 눈물을 많이 흘리기도 한다.", "각막염의 원인은 크게 외상성, 비외상성으로 구분할 수 있다. 외상성은 말 그대로 외부 물질이 각막을 자극해서 생기는 염증으로, 대표적인 예로 눈썹이 눈을 찌르거나, 샴푸 등의 약품이 눈을 자극해서 발생하거나, 날카로운 것에 의해 상처를 입는 등이 있다. 비외상성은 곰팡이, 세균, 바이러스 등에 의한 감염, 대사장애, 알레르기 반응 등이 있다. 각막염을 부르는 바이러스 감염 중 대표적인 질병은 개 디스템퍼 바이러스, 개 전염성간염 바이러스가 있다.", "샴푸 등 화학물질이 눈에 들어가지 않도록 목욕을 할 때 눈 부위와 가까운 부위는 최대한 피한다. 만일 샴푸가 눈에 들어가 개가 아파한다면 생리식염수를 눈 부위에 흘려주어 화학물질이나 티끌, 이물질이 더 이상 퍼지지 않도록 씻어주는 효과가 있다. 다만 이후에도 눈을 계속 뜨고 있지 못하면 즉시 병원으로 옮겨 치료를 받도록 한다.")
        insertDisease(db, "유루증", "강아지", "계속 눈물이 흐르는 만큼 눈 주위의 털이 쉽게 더러워지거나 냄새가 날 수 있다. 흰색 털을 가진 개들은 눈가의 흰색 털이 갈색으로 변하기도 한다. 눈물이 흘러내리는 부위에 피부가 붉어지거나 붓고 털이 빠지는 등의 피부염 증상을 보일 수도 있다. 이 경우 가려움증을 호소하며 눈 부위를 이불에 비비거나 앞발로 긁으려 할 수 있어 증상이 더 악화될 수 있다.", "유루증의 원인은 매우 다양해 여러 가지 요인이 복합적으로 작용하는 경우가 많다. 알러지, 작은 누점, 비루관의 구조적인 폐색, 안검내번, 첩모난생(속눈썹증), 각막과 결막의 질환, 그 외 통증을 동반한 여러 눈질환이 대표적인 원인이다. 시추, 페키니즈, 치와와 등 단두종은 구조적으로 비루관이 좁아 유루증이 잘 발생하는 경향이 있다.", "간식, 개껌 등의 급여를 중단하거나 사료를 교체하는 것이 유루증을 줄이는 데 도움이 될 수 있다. 유루증이 있는 경우 최대한 눈 주변의 털을 짧고 청결하게 유지하는 것이 눈물로 인한 피부염을 예방할 수 있다. 유루증이 갑자기 발생하거나 심해지면 유루증 이외 별개인 질환의 2차적인 증상일 수 있으니 수의사 진찰을 받도록 한다.")
        insertDisease(db, "안구건조증", "강아지", "눈에 황색 의 눈꼽이 끼는 것이 강아지 안구건조증의 가장 대표적인 증상이다. 결막이 충혈되고 강아지 눈을 보면 건조하고 뻑뻑한 상태임을 알 수 있다. 질병이 진행되면 각막이 혼탁해 보이거나 색소가 침착되기도 하며 강아지가 바닥에 눈을 비비는 등의 증상이 나타난다.", "대부분 비정상적인 자가면역 반응에 의해 눈물샘이 손상을 입게 되었을 때 발생한다. 경우가 드물지만 당뇨, 갑상선 기능저하증, 눈 기형, 약물 부작용 등으로 인해 발생할 수 있다. 눈물샘 제거 수술 시, 너무 많이 제거하면 안구건조증이 발생할 위험이 있다.", "강아지의 면역력을 증대시킬 수 있는 고른 영양섭취가 중요하며, 실내 환기나 먼지, 적정습도 유지 등의 환경 관리에 신경을 써주어야 한다. 피로감을 줄이고 충분한 숙면 또한 기본적인 예방법이다. 발병하면 완치되기 힘든 질병으로 관리를 중요시해야 한다.")

//        insertDisease(db, "결막염", "고양이", "눈동자가 충혈되거나 눈 주변이 빨갛게 부어오른다. 이물감을 느껴 과도한 깜빡임을 보이고 눈을 잘 뜨지 못하고 눈을 비비는 모습이 관찰된다. 하얀색 혹은 노란색 눈곱이 나오기도 하며, 피고름을 보이기도 한다. 세균 및 바이러스가 눈에서 호흡기로 퍼진 경우에는 재채기와 기침, 콧물을 보이기도 한다.", "결막염은 세균, 바이러스, 곰팡이, 기생충 등에 감염되어 발생하는 경우가 가장 흔하다. 알레르기가 있거나 눈에 속눈썹 등의 이물질이 눈에 들어가는 경우에도 발생할 수 있다. 또 눈꺼풀 종양이 있는 경우에도 마찰로 자극이 되어 결막염이 생길 수 있다.", "감염성 결막염을 예방하기 위해서는 감염된 다른 고양이와의 접촉을 최소화한다. 환경을 깨끗하게 하고 주기적으로 눈 상태를 관찰하는 것이 도움이 될 수 있다.")
//        insertDisease(db, "백내장", "고양이", "백내장 초기에는 시력에 큰 영향을 주지 않기에 별다른 증상을 느끼지 못할 수 있다. 시간이 지나 백내장이 많이 진행되면 검은 눈동자가 뿌옇고 하얗게 변하는 모습이 관찰된다. 이렇게 시력에 문제가 생기면 잘 걷지 못하고 벽이나 가구에 부딪히거나, 사료 및 간식을 잘 찾아 먹지 못하는 등의 문제가 발생한다. 잘 보이지 않아 점프나 착지에 실패하고, 넘어지는 경우도 있다. 불안해 구석에 숨거나 움츠러드는 행동 역시 보인다.", "백내장은 다양한 원인으로 발생한다. 사람처럼 노화로 인해 백내장이 생기는 경우가 많으며, 유전적 요인으로도 발생한다. 당뇨병이나 고혈압도 백내장을 유발할 수 있다. 외상으로 눈이 손상되거나, 눈에 염증이 생긴 경우, 수정체가 탈구되는 구조적인 이상으로 백내장이 발생하기도 한다. 그밖에 특정 단백질을 대사하지 못하는 등의 대사 문제나, 영양실조로도 발생할 수 있다.", "과도한 자외선에 노출되면 백내장이 심화될 수 있기 때문에 직사광선을 쬐지 않도록 유의한다. 평소 주기적인 건강검진으로 백내장을 유발할 수 있는 질환을 파악하고 관리한다.")
        insertDisease(db, "포도막염", "고양이", "일반적으로 포도막염은 통증을 유발하기 때문에 눈을 가늘게 뜨고, 빛에 민감하게 반응해 눈을 찌푸리거나, 눈물을 흘릴 수 있다. 또 눈 크기가 작아 보일 수 있으며, 녹내장이 동반되면 눈이 커지거나 튀어나온 것처럼 보일 수 있다. 홍채의 색이 평소와 달라 보이거나 동공이 붉게 보이는 적목현상도 보일 수 있다.", "포도막염이 나타날 수 있는 원인은 다양하다. 나뭇가지나 다른 동물의 발톱 등 긁힘에 의해 외상이 생겨서 발생할 수 있으며, 림프종과 홍채 흑색종 같은 암에 의해서도 나타난다. 또 고양이 전염성 복막염, 톡소 플라즈마, 고양이 백혈병 바이러스, 면역결핍 바이러스 등에 의해서도 발생할 수 있다.", "포도막염 재발을 관리하기 위해 1년에 3~4번 안과 검진을 해야 한다.")
        insertDisease(db, "각막염", "고양이", "각막에는 많은 신경이 분포돼 있어 염증이 생기면 큰 통증을 느낀다. 따라서 눈을 계속 감고 있거나 깜빡거릴 수 있다. 혹은 앞발로 눈을 문지르고 있어 한눈에 봐도 눈에 이상이 있다고 짐작 가능하다. 통증이 더 심해지면 얼굴을 바닥에 문지르며 아파하고, 눈물을 많이 흘리기도 한다.", "각막염의 원인은 크게 외상성, 비외상성으로 구분할 수 있다. 외상성은 말 그대로 외부 물질이 각막을 자극해서 생기는 염증으로, 대표적인 예로 눈썹이 눈을 찌르거나, 샴푸 등의 약품이 눈을 자극해서 발생하거나, 날카로운 것에 의해 상처를 입는 등이 있다. 비외상성은 곰팡이, 세균, 바이러스 등에 의한 감염, 대사장애, 알레르기 반응 등이 있다. 각막염을 부르는 바이러스 감염 중 대표적인 질병은 개 디스템퍼 바이러스, 개 전염성간염 바이러스가 있다.", "샴푸 등 화학물질이 눈에 들어가지 않도록 목욕을 할 때 눈 부위와 가까운 부위는 최대한 피한다. 만일 샴푸가 눈에 들어가 아파한다면 생리식염수를 눈 부위에 흘려주어 화학물질이나 티끌, 이물질이 더 이상 퍼지지 않도록 씻어주는 효과가 있다. 다만 이후에도 눈을 계속 뜨고 있지 못하면 즉시 병원으로 옮겨 치료를 받도록 한다.")
        insertDisease(db, "녹내장", "고양이", "안압이 높으면 고양이가 눈에 통증을 느껴 눈을 찌푸리는 증상을 보인다. 우울해하거나 구석에 숨어있기도 한다. 눈에서 눈물이 흐르기도 하며, 눈의 흰자위(공막)가 붉게 충혈되거나 부어 보인다. 또 각막이 흐리거나 푸르스름하게 보이기도 한다. 이미 신경이 손상된 경우 앞을 보지 못하는 실명 증상을 나타낸다.", "안방수가 배출되지 못해 안압이 오르게 되는 원발성 녹내장과, 다른 안과 질환이나 눈 부상으로 인해 우각 통로가 좁아지거나, 막히면서 안압이 오르는 이차성 녹내장이 있다. 이외에도, 종양이나 외상이 원인이 될 수 있다.", "녹내장을 완전히 예방할 수 있는 방법은 없다. 평소에 가지고 있는 포도막염 등의 안과 질환을 치료하고, 안압이 높은 경우라면 안압을 낮추는 안약을 점안하며 정기적인 안압 체크를 받는다.")


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DISEASES")
        onCreate(db)
    }

    private fun insertDisease(db: SQLiteDatabase, name: String, species: String, symptoms: String, causes: String, treatments: String) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_SPECIES, species)
        values.put(COLUMN_SYMPTOMS, symptoms)
        values.put(COLUMN_CAUSES, causes)
        values.put(COLUMN_TREATMENTS, treatments)
        val rowId = db.insert(TABLE_DISEASES, null, values)
        Log.d("DBHelper", "Inserted disease: $name, Species: $species, Row ID: $rowId")

    }

//    fun insertDisease(disease: Disease) {
//        val db = writableDatabase
//
//        val values = ContentValues().apply {
//            put(COLUMN_NAME, disease.name)
//            put(COLUMN_SYMPTOMS, disease.symptoms)
//            put(COLUMN_SPECIES, disease.species)
//            put(COLUMN_CAUSES, disease.causes)
//            put(COLUMN_TREATMENTS, disease.treatments)
//        }
//
//        db.insert(TABLE_DISEASES, null, values)
//        db.close()
//    }

    fun getDiseaseInformation(diseaseName: String): Disease? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_DISEASES,
            arrayOf(COLUMN_NAME, COLUMN_SPECIES, COLUMN_SYMPTOMS, COLUMN_CAUSES, COLUMN_TREATMENTS),
            "$COLUMN_NAME=?",
            arrayOf(diseaseName),
            null,
            null,
            null
        )

        var disease: Disease? = null
        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
            val speciesIndex = cursor.getColumnIndex(COLUMN_SPECIES)
            val symptomsIndex = cursor.getColumnIndex(COLUMN_SYMPTOMS)
            val causesIndex = cursor.getColumnIndex(COLUMN_CAUSES)
            val treatmentsIndex = cursor.getColumnIndex(COLUMN_TREATMENTS)

            if (nameIndex >= 0 && symptomsIndex >= 0 && causesIndex >= 0 && treatmentsIndex >= 0) {
                val name = cursor.getString(nameIndex)
                val species = cursor.getString(speciesIndex)
                val symptoms = cursor.getString(symptomsIndex)
                val causes = cursor.getString(causesIndex)
                val treatments = cursor.getString(treatmentsIndex)
                disease = Disease(name,species, symptoms, causes, treatments)
            } else {
                Log.d("Cursor Error", "One or more column indexes are invalid.")
            }
        } else {
            Log.d("Cursor Error", "Cursor is empty.")
        }

        cursor.close()
        db.close()
        return disease
    }

    fun getAllDiseases(): List<Disease> {
        val diseaseList = mutableListOf<Disease>()
        val db = readableDatabase
        val cursor = db.query(TABLE_DISEASES, null, null, null, null, null, null)

        val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
        val speciesIndex = cursor.getColumnIndex(COLUMN_SPECIES)
        val symptomsIndex = cursor.getColumnIndex(COLUMN_SYMPTOMS)
        val causesIndex = cursor.getColumnIndex(COLUMN_CAUSES)
        val treatmentsIndex = cursor.getColumnIndex(COLUMN_TREATMENTS)

        while (cursor.moveToNext()) {
            val name = cursor.getString(nameIndex)
            val species = cursor.getString(speciesIndex)
            val symptoms = cursor.getString(symptomsIndex)
            val causes = cursor.getString(causesIndex)
            val treatments = cursor.getString(treatmentsIndex)
            val disease = Disease(name, species, symptoms, causes, treatments)
            diseaseList.add(disease)
        }

        cursor.close()
        db.close()
        return diseaseList
    }

    fun getSpeciesDisease(diseaseSpecies : String):Disease?{
            val db = readableDatabase
            val cursor = db.query(
                TABLE_DISEASES,
                arrayOf(COLUMN_NAME, COLUMN_SPECIES, COLUMN_SYMPTOMS, COLUMN_CAUSES, COLUMN_TREATMENTS),
                "$COLUMN_SPECIES=?",
                arrayOf(diseaseSpecies),
                null,
                null,
                null
            )

            var disease: Disease? = null
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
                val speciesIndex = cursor.getColumnIndex(COLUMN_SPECIES)
                val symptomsIndex = cursor.getColumnIndex(COLUMN_SYMPTOMS)
                val causesIndex = cursor.getColumnIndex(COLUMN_CAUSES)
                val treatmentsIndex = cursor.getColumnIndex(COLUMN_TREATMENTS)

                if (nameIndex >= 0 && symptomsIndex >= 0 && causesIndex >= 0 && treatmentsIndex >= 0) {
                    val name = cursor.getString(nameIndex)
                    val species = cursor.getString(speciesIndex)
                    val symptoms = cursor.getString(symptomsIndex)
                    val causes = cursor.getString(causesIndex)
                    val treatments = cursor.getString(treatmentsIndex)
                    disease = Disease(name,species, symptoms, causes, treatments)
                } else {
                    Log.d("Cursor Error", "One or more column indexes are invalid.")
                }
            } else {
                Log.d("Cursor Error", "Cursor is empty.")
            }

            cursor.close()
            db.close()
            return disease
    }

    data class Disease(val name : String,val species : String,  val symptoms: String, val causes: String, val treatments: String)
}