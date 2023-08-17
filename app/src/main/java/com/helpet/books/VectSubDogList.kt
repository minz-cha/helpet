package com.helpet.books

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.helpet.R
import com.helpet.databinding.FragmentVectSubDogListBinding


class VectSubDogList : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentVectSubDogListBinding.inflate(inflater, container, false)
//
//        val items = mutableListOf<SampleData>()
//
//        items.add(SampleData( "결막염"))
//        items.add(SampleData( "백내장"))
//        items.add(SampleData( "안검하수"))
//
//        val adapter = BooksAdapter(items)
//        listView.adapter = adapter
//
//        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
//            val item = parent.getItemAtPosition(position) as SampleData
//            Toast.makeText(requireContext(), item.booksSubTitle, Toast.LENGTH_SHORT).show()
//        }

        binding.booksSubTitle1.setOnClickListener{
            val intent = Intent(requireContext(), VectDetail::class.java)
            val string = "1"
            intent.putExtra("string", string)
            startActivity(intent)
        }

        binding.booksSubTitle2.setOnClickListener{
            val intent = Intent(requireContext(), VectDetail::class.java)
            val string = "2"
            intent.putExtra("string", string)
            startActivity(intent)
        }
        binding.booksSubTitle3.setOnClickListener{
            val intent = Intent(requireContext(), VectDetail::class.java)
            val string = "3"
            intent.putExtra("string", string)
            startActivity(intent)
        }
        binding.booksSubTitle4.setOnClickListener{
            val intent = Intent(requireContext(), VectDetail::class.java)
            val string = "4"
            intent.putExtra("string", string)
            startActivity(intent)
        }
        binding.booksSubTitle5.setOnClickListener{
            val intent = Intent(requireContext(), VectDetail::class.java)
            val string = "5"
            intent.putExtra("string", string)
            startActivity(intent)
        }


        return binding.root
    }

}