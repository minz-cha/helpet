package com.helpet.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.helpet.books.Disease.Companion.toDisease
import com.helpet.databinding.ActivityVectListBinding

class VectList : AppCompatActivity() {

    private lateinit var binding: ActivityVectListBinding
    private val dbHelper: DBHelper = DBHelper(this)
    private lateinit var diseaseInfo: DBHelper.Disease
    private lateinit var checkedName: MutableList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVectListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAllList()

        val chipGroup = binding.chip
        checkedName = mutableListOf()

        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            Log.d("checkedIds", checkedIds.toString())
            checkedName = IdTodisease(checkedIds)
            Log.d("checkedName", checkedName.toString())

            if (checkedName.isEmpty()) {
                getAllList()
            } else {
                val selectedDiseases = mutableListOf<Disease>()
                for (check in checkedIds) {
                    val selectedChip = group.findViewById<Chip>(check)
                    Log.d("selectedChip", selectedChip.text.toString())
//                    val disease: DBHelper.Disease = if (check == "강아지" || check == "고양이") {
//                        dbHelper.getSpeciesDisease(check)!!
//                    } else {
                        val disease = dbHelper.getDiseaseInformation(selectedChip.text.toString())!!
//                    }
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

        val disease: MutableList<String> = mutableListOf()

        Log.d("id", "id")
        for (i in id) {
            when (i) {
                3 -> disease.add("강아지")
                4 -> disease.add("고양이")
                5 -> disease.add("결막염")
                6 -> disease.add("백내장")
                7 -> disease.add("각막궤양")
                8 -> disease.add("유루증")
                9 -> disease.add("안검내반증")
                10 -> disease.add("안검염")
                11 -> disease.add("궤양성각막질환")
                12 -> disease.add("비궤양성각막질환")
                13 -> disease.add("색소침착성각막염")
                14 -> disease.add("핵경화")
                15 -> disease.add("각막부골편")
                16 -> disease.add("비궤양성각막염")

            }
        }

        return disease

    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
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

