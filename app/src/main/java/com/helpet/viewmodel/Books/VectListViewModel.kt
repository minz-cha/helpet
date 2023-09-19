package com.helpet.viewmodel.Books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.BookDisease
import com.example.domain.usecase.BookUseCase
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VectListVIewModel @Inject constructor(private val useCase: BookUseCase) : ViewModel() {

    //chip 선택된 아이디 리스트
    private val _checkId = MutableLiveData<List<Int>>()
    val checkId : LiveData<List<Int>> get() = _checkId

    //chip 선택된 아이디값의 텍스트 리스트
    private val _checkedStr = MutableLiveData<List<String>>()
    val checkedStr : LiveData<List<String>> get() = _checkedStr

    fun getCheckId(checked : List<Int>){
        _checkId.value = checked
    }

    fun getCheckedStr(checkedStr: List<String>){
        _checkedStr.value = checkedStr
    }


    fun getAllList(): List<BookDisease> {
        return useCase.getAllDisease()
    }

    fun selectedBooks(): List<BookDisease> {
        val checkedText = _checkedStr.value!!
        val selectedDiseases = mutableListOf<BookDisease>()

        val selectedList = useCase.getDiseaseInformation(checkedText)


        return selectedList
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