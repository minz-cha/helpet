package com.helpet.vector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.helpet.R
import kotlinx.android.synthetic.main.activity_choice_pet_species.*

class ChoicePetSpecies : AppCompatActivity() {

    var petSpecies = ""
    private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_pet_species)


        back.setOnClickListener {
            val intent = Intent(this, VectorChoicePet::class.java)
            startActivity(intent)
        }

        petSpeciesDog.setOnClickListener {
            petSpeciesDog.setImageResource(R.drawable.choicedog)
            petSpecies = "강아지"
            val intent = Intent(this, VectorCamera::class.java)
            intent.putExtra("petSpecies", petSpecies)
            startActivity(intent)

        }
        petSpeciesCat.setOnClickListener {
            petSpeciesDog.setImageResource(R.drawable.choicecat)
            petSpecies = "고양이"
            val intent = Intent(this, VectorCamera::class.java)
            intent.putExtra("petSpecies", petSpecies)
            startActivity(intent)
        }

    }
}