package com.example.guessphraseapp2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guessphraseapp2.R.id.rvMain


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var phrase: String
    lateinit var displayphrase: TextView
    lateinit var userinput: EditText
    lateinit var codedphrase: CharArray
    lateinit var myRV: RecyclerView
    lateinit var guesses: ArrayList<String>
    private var counter = 10
    private var score = 0
    private var highscore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        highscore = sharedPreferences.getInt("highscore", 0)

        phrase = "hello boy"
        codedphrase = "*".repeat(phrase.length).toCharArray()
        displayphrase = findViewById(R.id.textView)
        userinput = findViewById(R.id.editTextTextPersonName)
        guesses = arrayListOf()
        var button = findViewById<Button>(R.id.button)
        myRV = findViewById(rvMain)
        var secretphrase = "Phrase: ${String(codedphrase)} \t High Score:$highscore"
        displayphrase.text = secretphrase

        button.setOnClickListener {

            var length = userinput.text.length
            if (length == 1) {
                checkLetter()
            } else {
                checkPhrase()
            }
        }
        myRV.adapter = RecyclerViewAdapter(guesses)
        myRV.layoutManager = LinearLayoutManager(this)
    }

    fun checkLetter() {
        var i = 0
        val userletter = userinput.text.trim()[0]
        val numberofocuurance = phrase.filter { it == userletter }.count()
        if (userletter in phrase) {
            while (i < phrase.length) {

                if (userletter == phrase[i]) {
                    codedphrase[i] = phrase[i]
                }
                i++
            }
            guesses.add("Found $numberofocuurance $userletter (s)")
        } else {
            guesses.add("Sorry,Its wrong Guess ")
            guesses.add("${--counter} Guesses Rermaining ")
            score++
            save()
        }
        if (counter == 0) {
            guesses.add("The Game Is Ended! ")
        }
        var secretphrase = "Phrase: ${String(codedphrase)} Guessed Letter: $userletter"
        displayphrase.text = secretphrase
        myRV.adapter?.notifyDataSetChanged()
        userinput.hint = "Enter The Phrase "
    }

    fun checkPhrase() {
        var c = userinput.text.toString().trim()
        if (c.equals(phrase)) {
            guesses.add(("Thats Right!!! Its :" + phrase))
        } else {
            guesses.add(("Wrong Guess:I don't Know"))
        }
        myRV.adapter?.notifyDataSetChanged()
        userinput.hint = "Enter The Letter"
    }

    fun save() {
        if (score<highscore) {
            with(sharedPreferences.edit()) {
                putInt("highscore", score)
                apply()
            }
        }
    }
}

