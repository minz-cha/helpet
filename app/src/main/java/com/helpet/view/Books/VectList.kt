package com.helpet.view.Books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.helpet.databinding.ActivityVectListBinding
import com.helpet.viewmodel.Books.VectListVIewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VectList : AppCompatActivity() {

    private val viewModel: VectListVIewModel by viewModels()

    private lateinit var binding: ActivityVectListBinding
    private lateinit var adapter : BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVectListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //아무것도 선택되지 않은 첫 화면
        val booklist = viewModel.getAllList()
        Log.d("booklist", booklist.toString())
        adapter = BookAdapter(this)
        binding.bookListView.layoutManager = LinearLayoutManager(this)
        binding.bookListView.adapter = adapter

        //chip 선택메소드
        binding.chip.setOnCheckedStateChangeListener { group, checkedIds ->
            Log.d("checkedIds", checkedIds.toString())
            val chipName : MutableList<String> = mutableListOf()
            for (checkedId in checkedIds) {
                val selectedChip = findViewById<Chip>(checkedId)
                val chipText = selectedChip.text.toString()
                chipName.add(chipText)
            }

            viewModel.getCheckId(checkedIds)
            viewModel.getCheckedStr(chipName)
        }

        //선택된 chip 관찰
        viewModel.checkedStr.observe(this){checkedIds->
            val selectedList = viewModel.selectedBooks()
            adapter.submitList(selectedList)

        }
    }


}