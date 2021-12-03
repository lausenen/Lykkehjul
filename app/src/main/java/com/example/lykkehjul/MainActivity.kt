package com.example.lykkehjul

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lykkehjul.databinding.ActivityMainBinding


//Game should be played on a pixel 2 device
//Github is
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
  private val _scores = (1..8)
    private var totalScore = 0
    private var lives = 5
    private val categories = Categories()
    private lateinit var category: Categories.Category
    private lateinit var randWord: MutableList<Categories.Category.singleChar>
    private var score = 0
    private var canSubmit = true
    private var canSpin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categories.initializeCategories()
        category = categories.getRandomCategory()
        randWord = getRandomWord()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.livesText.setText("Lives: " + lives.toString())
        binding.categoryText.setText("The category is " + category.name)
        binding.spinButton.setOnClickListener{
            if(canSpin) {
                spinWheel()
            }
        }
        binding.submitButton.setOnClickListener{
            if(validateInput()){
                checkCharCorrect()
            }
        }



        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = GridLayoutManager(this,6)
        recyclerView.adapter = CharAdapter(randWord)



    }

// Most of validationInput from video https://www.youtube.com/watch?v=e2ZyH0ZXmCY
    private fun validateInput() : Boolean{
    //Checks if there is only one character and that the field is not empty
        val inputField = binding.charTextinput
        if(inputField.text.toString().isEmpty()){
            inputField.error = "Inputfield should not be blank"
            return false
        }
    if(1<inputField.text.length){
        inputField.error = "Only one character allowed at a time"
        return false
    }

    if(!canSubmit){
        inputField.error = "You need to spin the wheel first"
        return false
    }

    //Decided not to do special characters
        return true
    }

    private fun checkCharCorrect(){
        val input = binding.charTextinput.text.toString()
        var correctMultiplier = 0
        for((index, singleChar) in randWord.withIndex()){
            if(singleChar.char.toString().equals(input) && !singleChar.isVisible){
                correctMultiplier ++
                singleChar.isVisible = true
                binding.recyclerView.adapter?.notifyItemChanged(index)
            }
        }

            println("CorrectMultiplier" + correctMultiplier)
            println("Score" + score)
                if(correctMultiplier == 0){
                    lives--
                    binding.livesText.setText("Lives: " + lives.toString())
                }
            else{
                    for (i in 1..correctMultiplier){
                        totalScore +=score
                    }
                    binding.scoreTotal.setText(totalScore.toString() + "Kr")
                }
        canSubmit = false
        canSpin = true
    }
    private fun getCategoryName() : String{
        return category.name
    }
    private fun getRandomWord() : MutableList<Categories.Category.singleChar>{
        return category.getRandomWord()
    }


    //Lands on a random number from 1-8 does an action depending on where the wheel lands
    private fun spinWheel() {
        if(!canSpin){
            binding.spinButton.setError("You need to enter a character first")
        }
        canSpin = false
        canSubmit = true

        when (_scores.random()) {
            1 -> {score = 1000
                binding.scoreCurrent.setText("Landed on: " + score + "Kr")
            }
            2 -> {score = 500
                binding.scoreCurrent.setText("Landed on: " + score + "Kr")
            }
            3 -> {
                score = 800
                binding.scoreCurrent.setText("Landed on: " + score + "Kr")
            }
            4 -> {
                lives -= 1
                binding.scoreCurrent.setText("Landed on: Skip turn \n Spin Again")
                binding.livesText.setText("Lives: " + lives.toString())
                canSpin = true
                canSubmit = false
            }
            5 -> {
                score = 666
                binding.scoreCurrent.setText("Landed on: " + score + "Kr")
            }
            6 -> {
                score = 700
                binding.scoreCurrent.setText("Landed on: " + score + "Kr")
            }
            7 -> {
                lives += 1
                binding.scoreCurrent.setText("Landed on: Extra turn")
                binding.livesText.setText("Lives: " + lives.toString())
            }
        }
    }

}