package com.helpet.books

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.helpet.R
import com.helpet.databinding.FragmentVectSubCatListBinding
import com.helpet.databinding.FragmentVectSubDogListBinding


class VectSubCatList : Fragment() {

    private lateinit var binding: FragmentVectSubCatListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentVectSubCatListBinding.inflate(inflater, container, false)


        binding.booksSubTitle6.setOnClickListener{
            val intent = Intent(requireContext(), VectDetailCat::class.java)
            val string = "6"
            intent.putExtra("string2", string)
            startActivity(intent)
        }

        binding.booksSubTitle7.setOnClickListener{
            val intent = Intent(requireContext(), VectDetailCat::class.java)
            val string = "7"
            intent.putExtra("string2", string)
            startActivity(intent)
        }
        binding.booksSubTitle8.setOnClickListener{
            val intent = Intent(requireContext(), VectDetailCat::class.java)
            val string = "8"
            intent.putExtra("string2", string)
            startActivity(intent)
        }
        binding.booksSubTitle9.setOnClickListener{
            val intent = Intent(requireContext(), VectDetailCat::class.java)
            val string = "9"
            intent.putExtra("string2", string)
            startActivity(intent)
        }
        binding.booksSubTitle10.setOnClickListener{
            val intent = Intent(requireContext(), VectDetailCat::class.java)
            val string = "10"
            intent.putExtra("string2", string)
            startActivity(intent)
        }


        return binding.root
    }

}