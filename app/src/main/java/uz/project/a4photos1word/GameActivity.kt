package uz.project.a4photos1word

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.core.view.isVisible
import uz.project.a4photos1word.core.Constants
import uz.project.a4photos1word.data.Question
import uz.project.a4photos1word.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private val questions = Constants.getQuestions()
    private lateinit var currentQuestion: Question
    private var index = -1
    private var countCycle = 0

    private var answersList = mutableListOf<TextView>()
    private var optionsList = mutableListOf<TextView>()
    private var userAnswer = mutableListOf<Pair<String, TextView>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (questions.isNotEmpty()) {
            currentQuestion = questions[++index]
        }

        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            fillAnswersList()
            fillOptionsList()

            tvOption1.setOnClickListener { setLetter(tvOption1) }
            tvOption2.setOnClickListener { setLetter(tvOption2) }
            tvOption3.setOnClickListener { setLetter(tvOption3) }
            tvOption4.setOnClickListener { setLetter(tvOption4) }
            tvOption5.setOnClickListener { setLetter(tvOption5) }
            tvOption6.setOnClickListener { setLetter(tvOption6) }
            tvOption7.setOnClickListener { setLetter(tvOption7) }
            tvOption8.setOnClickListener { setLetter(tvOption8) }
            tvOption9.setOnClickListener { setLetter(tvOption9) }
            tvOption10.setOnClickListener { setLetter(tvOption10) }
            tvOption11.setOnClickListener { setLetter(tvOption11) }
            tvOption12.setOnClickListener { setLetter(tvOption12) }

            tvAnswer1.setOnClickListener { removeLetter(tvAnswer1) }
            tvAnswer2.setOnClickListener { removeLetter(tvAnswer2) }
            tvAnswer3.setOnClickListener { removeLetter(tvAnswer3) }
            tvAnswer4.setOnClickListener { removeLetter(tvAnswer4) }
            tvAnswer5.setOnClickListener { removeLetter(tvAnswer5) }
            tvAnswer6.setOnClickListener { removeLetter(tvAnswer6) }
            tvAnswer7.setOnClickListener { removeLetter(tvAnswer7) }
            tvAnswer8.setOnClickListener { removeLetter(tvAnswer8) }

            btnNext.setOnClickListener {
                if (++index < questions.size) {
                    currentQuestion = questions[index]
                } else {
                    index = 0
                    countCycle++
                    currentQuestion = questions[index]
                }
                setQuestion()
            }

            setQuestion()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        binding.apply {
            userAnswer.clear()
            setCongratsView(false)

            tvLevelNumber.text = (currentQuestion.id + countCycle * questions.size).toString()

            ivOne.setImageResource(currentQuestion.images[0])
            ivTwo.setImageResource(currentQuestion.images[1])
            ivThree.setImageResource(currentQuestion.images[2])
            ivFour.setImageResource(currentQuestion.images[3])

            repeat(currentQuestion.answer.length) {
                answersList[it].isVisible = true
                answersList[it].text = ""
            }

            repeat(8 - currentQuestion.answer.length) {
                answersList[it + currentQuestion.answer.length].isVisible = false
                answersList[it + currentQuestion.answer.length].text = ""
            }

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

            tvOption1.text = optionLetters[0].toString()
            tvOption2.text = optionLetters[1].toString()
            tvOption3.text = optionLetters[2].toString()
            tvOption4.text = optionLetters[3].toString()
            tvOption5.text = optionLetters[4].toString()
            tvOption6.text = optionLetters[5].toString()
            tvOption7.text = optionLetters[6].toString()
            tvOption8.text = optionLetters[7].toString()
            tvOption9.text = optionLetters[8].toString()
            tvOption10.text = optionLetters[9].toString()
            tvOption11.text = optionLetters[10].toString()
            tvOption12.text = optionLetters[11].toString()
        }
    }

    private fun fillAnswersList() {
        binding.apply {
            answersList.add(tvAnswer1)
            answersList.add(tvAnswer2)
            answersList.add(tvAnswer3)
            answersList.add(tvAnswer4)
            answersList.add(tvAnswer5)
            answersList.add(tvAnswer6)
            answersList.add(tvAnswer7)
            answersList.add(tvAnswer8)
        }
    }

    private fun fillOptionsList() {
        binding.apply {
            optionsList.add(tvOption1)
            optionsList.add(tvOption2)
            optionsList.add(tvOption3)
            optionsList.add(tvOption4)
            optionsList.add(tvOption5)
            optionsList.add(tvOption6)
            optionsList.add(tvOption7)
            optionsList.add(tvOption8)
            optionsList.add(tvOption9)
            optionsList.add(tvOption10)
            optionsList.add(tvOption11)
            optionsList.add(tvOption12)
        }
    }

    private fun setLetter(textView: TextView) {
        val letter = textView.text.toString()
        if (letter.isNotEmpty() && userAnswer.filter { it.first != "" }.size != currentQuestion.answer.length) {
            val pair = Pair(letter, textView)
            val emptyIndex = userAnswer.indexOf(Pair("", binding.tvAnswer1))
            if (emptyIndex == -1) {
                userAnswer.add(pair)
            } else {
                userAnswer[emptyIndex] = pair
            }
            textView.text = ""
            answersList[userAnswer.indexOf(pair)].text = letter
        }

        if (userAnswer.filter { it.first != "" }.size == currentQuestion.answer.length) {
            var answer = ""
            userAnswer.forEach {
                answer += it.first
            }
            if (answer == currentQuestion.answer) {
                setCongratsView(true)
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeLetter(textView: TextView) {
        val letter = textView.text.toString()
        if (letter.isNotEmpty()) {
            val index = answersList.indexOf(textView)
            val pair = userAnswer[index]
            textView.text = ""
            pair.second.text = pair.first

            // logic
            userAnswer[index] = Pair("", binding.tvAnswer1)
        }
    }

    private fun setCongratsView(success: Boolean) {
        binding.apply {
            viewOverlay.isVisible = success
            ivShine.isVisible = success
            val rotateAnimation =
                AnimationUtils.loadAnimation(this@GameActivity, R.anim.rotate_view)
            ivShine.startAnimation(rotateAnimation)
            if (!success) ivShine.clearAnimation()
            btnNext.isVisible = success
            tvNext.isVisible = success

            answersList.forEach { tvAnswer ->
                tvAnswer.isEnabled = !success
            }
            optionsList.forEach { tvOption ->
                tvOption.isEnabled = !success
            }
        }
    }
}
