package com.helpet.vector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.helpet.R
import com.helpet.databinding.ActivityChoicePetSpeciesBinding

class ChoicePetSpecies : AppCompatActivity() {

    var petSpecies = ""
    private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityChoicePetSpeciesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener {
            val intent = Intent(this, VectorChoicePet::class.java)
            startActivity(intent)
        }

        binding.petSpeciesDog.setOnClickListener {
            binding.petSpeciesDog.setImageResource(R.drawable.choicedog)
            petSpecies = "강아지"
            val intent = Intent(this, VectorCamera::class.java)
            intent.putExtra("petSpecies", petSpecies)
            startActivity(intent)

        }
        binding.petSpeciesCat.setOnClickListener {
            binding.petSpeciesDog.setImageResource(R.drawable.choicecat)
            petSpecies = "고양이"
            val intent = Intent(this, VectorCamera::class.java)
            intent.putExtra("petSpecies", petSpecies)
            startActivity(intent)
        }

    }
}