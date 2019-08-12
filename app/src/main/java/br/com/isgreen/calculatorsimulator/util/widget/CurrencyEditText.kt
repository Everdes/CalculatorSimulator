package br.com.isgreen.calculatorsimulator.util.widget

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Created by Éverdes Soares on 08/10/2019
 */

class CurrencyEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor (context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        this.inputType = InputType.TYPE_CLASS_NUMBER
        this.addTextChangedListener(onTextChange)
    }

    fun getValue(): Double {
        return if (this.text.isNullOrBlank()) 0.0 else getValue(this.text.toString())
    }

    fun getValue(text: String): Double {
        val symbols = "[R$,.]"

        val cleanString = text.replace(symbols.toRegex(), "")
            .replace("\\s".toRegex(), "")

        val parsed = cleanString.toDouble()
        return parsed / 100
    }

    override fun onSelectionChanged(start: Int, end: Int) {

        val text = text
        if (text != null) {
            if (start != text.length || end != text.length) {
                setSelection(text.length, text.length)
                return
            }
        }

        super.onSelectionChanged(start, end)
    }

    private fun moneyFormatter(value: Double): String {
        val fmt = NumberFormat.getInstance() as DecimalFormat
        val symbol = "R$"
        fmt.isGroupingUsed = true
        fmt.positivePrefix = "$symbol "
        fmt.negativePrefix = "-$symbol "
        fmt.minimumFractionDigits = 2
        fmt.maximumFractionDigits = 2
        return fmt.format(value)
    }

    private val onTextChange = object : TextWatcher {

        private var oldValue: Double = 0.toDouble()
        private var isUpdating: Boolean = false

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            if (!TextUtils.isEmpty(s)) {
                oldValue = getValue(s.toString())
            }
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            if (isUpdating) {
                isUpdating = false
                return
            }

            val text =
                if (TextUtils.isEmpty(s.toString())) moneyFormatter(0.0) else s.toString()

            var value = getValue(text)

            if (value > 1_000_000.0) {
                value = oldValue
            }

            val formatted = moneyFormatter(value)

            isUpdating = true

            this@CurrencyEditText.setText(formatted)
            this@CurrencyEditText.setSelection(formatted.length)
        }
    }

}