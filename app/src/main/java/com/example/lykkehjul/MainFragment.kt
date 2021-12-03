package com.example.lykkehjul

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lykkehjul.databinding.ActivityMainBinding
import com.example.lykkehjul.databinding.FragmentMainBinding

//Game should be played on a pixel 2 device is I have designed the layout for this device
//Github is linked here https://github.com/lausenen/Lykkehjul and is public,
// since i could not find the github usernames of teachers.
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val _scores = (1..8)
    private var totalScore = 0
    private var lives = 5
    private val categories = Categories()
    private lateinit var category: Categories.Category
    private lateinit var randWord: MutableList<Categories.Category.singleChar>
    private var score = 0
    private var canSubmit = true
    private var canSpin = true



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        categories.initializeCategories()
        category = categories.getRandomCategory()
        randWord = getRandomWord()

        //Sets up text and buttons
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


        //Set the recyclerview to only take 6 items on one row
        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = GridLayoutManager(context,6)
        recyclerView.adapter = CharAdapter(randWord)


        return binding.root
    }

    // Most of validationInput is from video https://www.youtube.com/watch?v=e2ZyH0ZXmCY
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


    //Checks if the character is in the word and awards points after how many
    private fun checkCharCorrect(){
        val input = binding.charTextinput.text.toString()
        var correctMultiplier = 0
        var visibleCount = 0
        for((index, singleChar) in randWord.withIndex()){
            if(singleChar.isVisible){
                visibleCount++
            }
            else if(singleChar.char.toString().equals(input) && !singleChar.isVisible){
                correctMultiplier ++
                singleChar.isVisible = true
                visibleCount ++
                binding.recyclerView.adapter?.notifyItemChanged(index)
            }
        }

        println("CorrectMultiplier" + correctMultiplier)
        println("Score" + score)
        if(correctMultiplier == 0){
            lives--
            binding.livesText.setText("Lives: " + lives.toString())
            if(lives == 0){
                findNavController().navigate(R.id.action_mainFragment_to_secondFragment)
            }
        }
        else{
            for (i in 1..correctMultiplier){
                totalScore +=score
            }
            binding.scoreTotal.setText(totalScore.toString() + "Kr")
        }

        //Makes it so that you cannot submit twice in a row
        canSubmit = false
        canSpin = true
        binding.charTextinput.setText("")

        //Checks for win
        if(visibleCount == randWord.size){
            findNavController().navigate(R.id.action_mainFragment_to_firstFragment)
        }

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

        //Sets it so that you cannot spin again until you have submitted
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
                if(lives == 0){
                    findNavController().navigate(R.id.action_mainFragment_to_secondFragment)
                }
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
                canSpin = true
                canSubmit = false
            }
        }
    }


}