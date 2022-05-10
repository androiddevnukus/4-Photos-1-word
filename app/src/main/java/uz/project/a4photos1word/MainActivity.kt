package uz.project.a4photos1word

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import uz.project.a4photos1word.core.Constants
import uz.project.a4photos1word.data.Question
import uz.project.a4photos1word.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var questions: List<Question>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        questions = Constants.getQuestions()

        val button = findViewById<TextView>(R.id.play_btn)
        button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        val levelIndex = sharedPreferences.getInt(GameActivity.LEVEL_INDEX, 0)
        setCurrentQuestion(levelIndex)
        super.onResume()
    }

    private fun setCurrentQuestion(index: Int) {
        val currentQuestion = questions[index]

        binding.apply {
            tvLevelNumber.text = currentQuestion.id.toString()

            ivImage1.setImageResource(currentQuestion.images[0])
            ivImage2.setImageResource(currentQuestion.images[1])
            ivImage3.setImageResource(currentQuestion.images[2])
            ivImage4.setImageResource(currentQuestion.images[3])
        }
    }
}
