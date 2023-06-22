package com.example.individual17m5

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.individual17m5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val currencies = arrayOf("USD", "EUR", "GBP") // Agrega más divisas según sea necesario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.currencySpinnerFrom.adapter = adapter
        binding.currencySpinnerTo.adapter = adapter

        binding.convertButton.setOnClickListener { convertCurrency() }
        binding.resetButton.setOnClickListener { resetFields() }
    }

    private fun convertCurrency() {
        val amount = binding.amountEditText.text.toString().toDoubleOrNull()
        if (amount != null) {
            val currencyFrom = binding.currencySpinnerFrom.selectedItem.toString()
            val currencyTo = binding.currencySpinnerTo.selectedItem.toString()
            val convertedAmount = convertAmount(amount, currencyFrom, currencyTo)
            binding.resultTextView.text = getString(R.string.converted_amount, convertedAmount, currencyTo)
        } else {
            Toast.makeText(this, "Ingrese un valor válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun convertAmount(amount: Double, currencyFrom: String, currencyTo: String): Double {
        // Aquí debes implementar la lógica para obtener la tasa de cambio actualizada y realizar la conversión
        // Retorna el monto convertido
        // Por ejemplo, aquí se asume que la tasa de cambio para USD a EUR es 0.85
        val rateFromTo = getExchangeRate(currencyFrom, currencyTo)
        val rateToFrom = getExchangeRate(currencyTo, currencyFrom)

        return when {
            currencyFrom == currencyTo -> amount
            rateFromTo != null -> amount * rateFromTo
            rateToFrom != null -> amount / rateToFrom
            else -> amount
        }
    }

    private fun getExchangeRate(currencyFrom: String, currencyTo: String): Double? {
        // Aquí debes implementar la lógica para obtener la tasa de cambio actualizada

        return when (currencyFrom) {
            "USD" -> when (currencyTo) {
                "EUR" -> 0.85
                "GBP" -> 0.72
                else -> null
            }
            "EUR" -> when (currencyTo) {
                "USD" -> 1.18
                "GBP" -> 0.85
                else -> null
            }
            "GBP" -> when (currencyTo) {
                "USD" -> 1.39
                "EUR" -> 1.17
                else -> null
            }
            else -> null
        }
    }

    private fun resetFields() {
        binding.amountEditText.text.clear()
        binding.resultTextView.text = ""
    }
}
