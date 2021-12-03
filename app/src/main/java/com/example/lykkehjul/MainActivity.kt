package com.example.lykkehjul

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lykkehjul.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
  private val _scores = (1..8)
    private var totalScore = 0
    private var lives = 5
    private val categories = Categories()
    private lateinit var category: Categories.Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categories.initializeCategories()
        category = categories.getRandomCategory()
        val randWord = getRandomWord()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.livesText.setText("Lives: " + lives.toString())

        binding.spinButton.setOnClickListener{
            spinWheel()
        }

        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = GridLayoutManager(this,6)
        recyclerView.adapter = CharAdapter(randWord)


    }


    private fun getCategoryName() : String{
        return category.name
    }
    private fun getRandomWord() : CharArray{
        return category.getRandomWord()
    }


    //Lands on a random number from 1-8 does an action depending on where the wheel lands
    private fun spinWheel() {
        when (_scores.random()) {
            1 -> setScore(1000)
            2 -> setScore(500)
            3 -> setScore(800)
            4 -> {
                lives -= 1
                binding.scoreCurrent.setText("Landed on: Skip turn")
                binding.livesText.setText("Lives: " + lives.toString())
            }
            5 -> setScore(666)
            6 -> setScore(700)
            7 -> {
                lives += 1
                binding.scoreCurrent.setText("Landed on: Extra turn")
                binding.livesText.setText("Lives: " + lives.toString())
            }
            8 -> setScore(-1)
        }

    }


   private fun setScore(score: Int) {
       val landedOn = "Landed on: "

       //Sets textviews to update the score and if it lands on bankrupt (-1) it resets the score
        if(score!=-1){
            totalScore += score

            binding.scoreCurrent.setText(landedOn + score + "Kr")
        }
        else{
            totalScore = 0
            binding.scoreCurrent.setText(landedOn + "Bankrupt")
        }


       val finalScore = totalScore.toString() + "Kr"
       binding.scoreTotal.setText(finalScore)
    }
}