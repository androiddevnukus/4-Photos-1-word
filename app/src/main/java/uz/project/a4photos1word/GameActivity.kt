package uz.project.a4photos1word

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import uz.project.a4photos1word.core.Constants
import uz.project.a4photos1word.data.Question
import uz.project.a4photos1word.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private val questions = Constants.getQuestions()
    private lateinit var currentQuestion: Question
    private var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (questions.isNotEmpty()) {
            currentQuestion = questions[++index]
        }

        binding.apply {
            setQuestion()
        }
    }

    private fun setQuestion() {
        binding.apply {
            tvLevelNumber.text = currentQuestion.id.toString()

            ivOne.setImageResource(currentQuestion.images[0])
            ivTwo.setImageResource(currentQuestion.images[1])
            ivThree.setImageResource(currentQuestion.images[2])
            ivFour.setImageResource(currentQuestion.images[3])

            // TODO: should be changed
            setAnswerBox()

            setOptionLetters()
        }
    }

    private fun setOptionLetters() {
        binding.apply {
            val optionLetters = mutableListOf<Char>()
            optionLetters.addAll(currentQuestion.answer.toList())

            repeat(12 - optionLetters.size) {
                optionLetters.add(Random.nextInt(1040, 1072).toChar())
            }
            optionLetters.shuffle()

            optionLetterOne.text = optionLetters[0].toString()
            optionLetterTwo.text = optionLetters[1].toString()
            optionLetterThree.text = optionLetters[2].toString()
            optionLetterFour.text = optionLetters[3].toString()
            optionLetterFive.text = optionLetters[4].toString()
            optionLetterSix.text = optionLetters[5].toString()
            optionLetterSeven.text = optionLetters[6].toString()
            optionLetterEight.text = optionLetters[7].toString()
            optionLetterNine.text = optionLetters[8].toString()
            optionLetterTen.text = optionLetters[9].toString()
            optionLetterEleven.text = optionLetters[10].toString()
            optionLetterTwelve.text = optionLetters[11].toString()
        }
    }

    private fun setAnswerBox() {
        currentQuestion.answer.forEach { letter ->
            val textView = TextView(this)
            textView.setTextAppearance(R.style.answers_box)
            textView.text = letter.toString()

            binding.containerOfAnswers.addView(textView)
        }
    }
}
