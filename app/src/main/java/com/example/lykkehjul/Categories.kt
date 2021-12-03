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
        val foodsWords = mutableListOf<String>("cabbage", "carrot" , "obachine", "guava", "peach")
        val sportsWords = mutableListOf<String>("tennis", "badminton" , "football", "wrestling")

        games.addWords(gamesWords)
        foods.addWords(foodsWords)
        sports.addWords(sportsWords)


    }

    //Returns a random category
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

        fun getRandomWord() : MutableList<singleChar>{
            //Selects random word and turns it into chars
            val randnum = Random.nextInt(0,words.size)
            val randWord = words.get(randnum)
            val randCharArray = randWord.toCharArray()

            //Makes the chars invisible but a few visible.
            val randnum1 = Random.nextInt(1,3)
            var randIndexes = mutableListOf<Int>()


            //Producing random indexes that will be visible
            for (i in 0..randnum1){
                println("i: " + i)
                //Can add the same random number resulting in one less visible letter but this is intentional :)
                randIndexes.add(i,Random.nextInt(0,randCharArray.size))
            }
            var isvisible = false
            var singleCharList = mutableListOf<singleChar>()

            //Sets the chars visibility
            for ((iterator, char) in randCharArray.withIndex()){
                for (randindex in randIndexes){
                    if (randindex == iterator){
                        isvisible = true
                    }
                }

                val singlechar = singleChar(isvisible,char)
                singleCharList.add(singlechar)
                isvisible = false
            }
            return singleCharList
        }

        //Simple data class to hold information if the character is visible or not
        data class singleChar(var isVisible: Boolean, val char: Char)

    }
}