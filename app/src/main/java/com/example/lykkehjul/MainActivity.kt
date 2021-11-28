package com.example.lykkehjul

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lykkehjul.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
  private val _SCORES = (1..8)
    private var totalScore = 0
    private var lives = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.livesText.setText("Lives: " + lives.toString())

        binding.spinButton.setOnClickListener{
            spinWheel()
        }

        /* setSupportActionBar(binding.toolbar)*/

        /*      val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)*/

        /*     binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

    //Lands on a random number from 1-8 does an action depending on where the wheel lands
    fun spinWheel() {
        when (_SCORES.random()) {
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


   fun setScore(score: Int) {
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

/*    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }*/
}