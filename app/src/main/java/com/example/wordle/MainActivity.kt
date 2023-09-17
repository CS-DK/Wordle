package com.example.wordle

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.wordle.R
import com.example.wordle.FourLetterWordList



class MainActivity : AppCompatActivity() {
    var guesses = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txt = findViewById<EditText>(R.id.usrTxt)
        val guessButton = findViewById<Button>(R.id.button)
        guessButton.setOnClickListener{
            val userGuess = txt.text.toString()
            if (guesses >= 3){
                Toast.makeText(it.context, "Guess Limit! Word: $wordToGuess", Toast.LENGTH_SHORT).show()
                val guessWord = findViewById<TextView>(R.id.word)
                guessWord.text = wordToGuess
                guessWord.visibility = View.VISIBLE
            }
            else {
                guesses++

                val correctness = checkGuess(userGuess)
                val correctnessTextView = TextView(this)
                correctnessTextView.textSize = 30f
                correctnessTextView.gravity = Gravity.CENTER_HORIZONTAL
                correctnessTextView.append("Guess #$guesses                     $userGuess  \nGuess #$guesses Check        $correctness")
                val resultsContainer = findViewById<LinearLayout>(R.id.resultsContainer)
                resultsContainer.addView(correctnessTextView)
                correctnessTextView.visibility = View.VISIBLE

                if (correctness == "OOOO"){
                    Toast.makeText(it.context, "Congratulation, you won!", Toast.LENGTH_SHORT).show()
                    guessButton.isEnabled = false
                    val guessWord = findViewById<TextView>(R.id.word)
                    guessWord.text = "You guessed the word!"
                    guessWord.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    val wordToGuess = FourLetterWordList.getRandomFourLetterWord().lowercase()
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}