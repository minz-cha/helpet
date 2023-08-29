package com.helpet.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.helpet.books.Disease.Companion.toDisease
import com.helpet.databinding.ActivityVectListBinding

class VectList : AppCompatActivity() {

    private lateinit var binding: ActivityVectListBinding
    private val dbHelper: DBHelper = DBHelper(this)
    private lateinit var diseaseInfo: DBHelper.Disease

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVectListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAllList()

        binding.chip.setOnCheckedStateChangeListener { group, checkedIds ->


            Log.d("checkedIds", checkedIds.toString())
            val checkedName = IdTodisease(checkedIds)

            if (checkedName.isEmpty()) {
                getAllList()
            } else {
                val selectedDiseases = mutableListOf<Disease>()
                for (check in checkedName) {
                    val disease: DBHelper.Disease = if (check == "강아지" || check == "고양이") {
                        dbHelper.getSpeciesDisease(check)!!
                    } else {
                        dbHelper.getDiseaseInformation(check)!!
                    }
                    selectedDiseases.add(disease.toDisease())
                }
                Log.d("selectedDisease", selectedDiseases.toString())

                val adapter = BookAdapter(this, selectedDiseases)
                binding.bookListView.layoutManager = LinearLayoutManager(this)
                binding.bookListView.adapter = adapter
            }
        }
    }


    //아무것도 선택되지 않았을 때 디폴트로 전체 리스트 보여주기
    private fun getAllList() {
        val allDiseases = dbHelper.getAllDiseases()

        val allSelDisease =
            allDiseases.map { Disease(it.name, it.symptoms, it.causes, it.treatments) }
        val adapter = BookAdapter(this, allSelDisease)
        binding.bookListView.layoutManager = LinearLayoutManager(this)
        binding.bookListView.adapter = adapter
    }

    private fun IdTodisease(id: List<Int>): MutableList<String> {

        var disease: MutableList<String> = mutableListOf()

        for (i in id) {
            when (i) {
                2 -> disease.add("강아지")
                3 -> disease.add("고양이")
                4 -> disease.add("결막염")
                5 -> disease.add("백내장")
                6 -> disease.add("각막궤양")
                7 -> disease.add("유루증")
                8 -> disease.add("안구건조증")
                9 -> disease.add("포도막염")
                10 -> disease.add("각막염")
                11 -> disease.add("녹내장")
            }
        }

        return disease

    }


}

data class Disease(
    val name: String,
    val symptoms: String,
    val cause: String,
    val treats: String
) {
    companion object {
        fun DBHelper.Disease.toDisease(): Disease {
            return Disease(this.name, this.symptoms, this.causes, this.treatments)
        }
    }
}

