package com.example.currencyconverter

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val amountEditText = findViewById<EditText>(R.id.amountEditText)
        val fromCurrencySpinner = findViewById<Spinner>(R.id.fromCurrencySpinner)
        val toCurrencySpinner = findViewById<Spinner>(R.id.toCurrencySpinner)
        val convertButton = findViewById<Button>(R.id.convertButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        val currencies = resources.getStringArray(R.array.currencies)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromCurrencySpinner.adapter = adapter
        toCurrencySpinner.adapter = adapter

        convertButton.setOnClickListener {
            val amount = amountEditText.text.toString().toDoubleOrNull()

            if (amount == null) {
                resultTextView.text = "Podaj prawidłową kwotę."
                return@setOnClickListener
            }

            val fromCurrency = fromCurrencySpinner.selectedItem.toString()
            val toCurrency = toCurrencySpinner.selectedItem.toString()

            if (fromCurrency == toCurrency) {
                resultTextView.text = "Tyle samo :)"
                return@setOnClickListener
            }

            val conversionRate = getConversionRate(fromCurrency, toCurrency)

            if (conversionRate != null) {
                val convertedAmount = amount * conversionRate
                resultTextView.text = "$amount $fromCurrency to $toCurrency: %.2f".format(convertedAmount)
            } else {
                resultTextView.text = "Nie można przeliczyć tej waluty."
            }
        }
    }

    private fun getConversionRate(fromCurrency: String, toCurrency: String): Double? {
        val rates = mapOf(
            "USD" to mapOf("EUR" to 0.85, "PLN" to 4.0),
            "EUR" to mapOf("USD" to 1.18, "PLN" to 4.5),
            "PLN" to mapOf("USD" to 0.25, "EUR" to 0.22)
        )

        return rates[fromCurrency]?.get(toCurrency)
    }
}
