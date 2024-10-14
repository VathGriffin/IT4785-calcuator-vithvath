package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorapp.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var resultText: TextView
    lateinit var operatorText: TextView
    var currentNumber: String = ""
    var operator: String = ""
    var firstNumber: Int = 0
    var secondNumber: Int = 0
    var calculationDone: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.result)
        operatorText = findViewById(R.id.operator)

        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.buttonPlus, R.id.buttonMinus, R.id.buttonX, R.id.buttonFSlash,
            R.id.buttonEqual, R.id.buttonC, R.id.buttonCE, R.id.buttonBS
        )
        buttons.forEach { findViewById<Button>(it).setOnClickListener(this) }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button0 -> appendNumber("0")
            R.id.button1 -> appendNumber("1")
            R.id.button2 -> appendNumber("2")
            R.id.button3 -> appendNumber("3")
            R.id.button4 -> appendNumber("4")
            R.id.button5 -> appendNumber("5")
            R.id.button6 -> appendNumber("6")
            R.id.button7 -> appendNumber("7")
            R.id.button8 -> appendNumber("8")
            R.id.button9 -> appendNumber("9")
            R.id.buttonPlus -> updateOperator("+")
            R.id.buttonMinus -> updateOperator("-")
            R.id.buttonX -> updateOperator("*")
            R.id.buttonFSlash -> updateOperator("/")
            R.id.buttonEqual -> calculateResult()
            R.id.buttonC -> clearAll()
            R.id.buttonCE -> clearEntry()
            R.id.buttonBS -> backspace()
        }
    }

    private fun appendNumber(number: String) {
        if (calculationDone) {
            clearAll()
            calculationDone = false
        }
        currentNumber += number
        resultText.text = currentNumber
    }

    private fun updateOperator(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber.toInt()
            currentNumber = ""
            operator = op
            operatorText.text = "$firstNumber $operator"
        } else if (operator.isNotEmpty()) {
            operator = op
            operatorText.text = "$firstNumber $operator"
        }
    }

    private fun calculateResult() {
        if (currentNumber.isNotEmpty() && operator.isNotEmpty()) {
            secondNumber = currentNumber.toInt()
            val result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0) firstNumber / secondNumber else "Error"
                else -> "Error"
            }
            operatorText.text = "$firstNumber $operator $secondNumber ="
            resultText.text = result.toString()
            currentNumber = result.toString()
            operator = ""
            calculationDone = true
        }
    }

    private fun clearEntry() {
        if (calculationDone) {
            clearAll()
        } else {
            resultText.text = "0"
            currentNumber = ""
        }
    }

    private fun clearAll() {
        currentNumber = ""
        firstNumber = 0
        secondNumber = 0
        operator = ""
        resultText.text = "0"
        operatorText.text = ""
        calculationDone = false
    }

    private fun backspace() {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
            resultText.text = if (currentNumber.isEmpty()) "0" else currentNumber
        }
    }
}