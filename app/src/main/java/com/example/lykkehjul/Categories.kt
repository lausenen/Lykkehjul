package com.example.lykkehjul

import kotlin.random.Random

class Categories {

    //Should use array to hold categories
    private var games = Category("games")
    private var foods = Category("foods")
    private var sports = Category("sports")

    fun initializeCategories(){


        //Would be better for performance using arrays, but im more comfortable using mutableList :)
        val gamesWords = mutableListOf<String>("minecraft", "pacman" , "tetris", "hearthstone")
        val foodsWords = mutableListOf<String>("cabbage", "carrot" , "obachine", "peach")
        val sportsWords = mutableListOf<String>("tennis", "badminton" , "football", "wrestling")

        games.addWords(gamesWords)
        foods.addWords(foodsWords)
        sports.addWords(sportsWords)


    }

    fun getRandomCategory(): Category {
        val randNum = Random.nextInt(1,4)
        when(randNum){
            1 -> return games
            2 -> return foods
            3 -> return sports

        }
        return games
    }


    class Category(name: String)
    {
        val name = name
        var words = mutableListOf<String>()

        fun addWords(list: MutableList<String>){
            words.addAll(list)
        }

        fun getRandomWord() : CharArray{
            //Selects random word and turns it into chars
            val randnum = Random.nextInt(0,words.size)
            val randWord = words.get(randnum)
            val randCharArray = randWord.toCharArray()

            //Makes the chars invisible but a few visible
            var singleCharList = mutableListOf<singleChar>()
            for (char in randCharArray){
                val singlechar = singleChar(false,char)
                singleCharList.add(singlechar)
            }
            return randCharArray
        }

        data class singleChar(var isVisible: Boolean, val char: Char)

    }
}