package br.com.isgreen.calculatorsimulator.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import br.com.isgreen.calculatorsimulator.R
import br.com.isgreen.calculatorsimulator.util.DateUtil
import java.text.NumberFormat

/**
 * Created Éverdes Soares on 08/07/2019.
 */

//region Context
fun Context.toastShort(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.toastShort(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

fun Context.toastLong(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.toastLong(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
//endregion Context

//region View
fun View?.hideKeyboard() {
    this?.let {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}
//endregion View

//region TextView
fun AppCompatTextView?.setCurrency(value: Double?) {
    this?.text = value?.let { NumberFormat.getCurrencyInstance().format(value) } ?: run { "-" }
}

fun AppCompatTextView?.setPercent(value: Double?) {
    this?.let { textView ->
        value?.let { textView.text = textView.resources.getString(R.string.percentage_value, value) }
            ?: run { "-" }
    }
}

fun AppCompatTextView?.setCurrencyAndPercent(value: Double?, rate: Double?) {
    this?.let {
        if (value != null && rate != null) {
            val currency = NumberFormat.getCurrencyInstance().format(value)
            val percent = it.resources.getString(R.string.percentage_value, ((value * rate) / 100))
            it.text = it.resources.getString(R.string.currency_and_percentage_value, currency, percent)
        }
    }
}

fun AppCompatTextView?.setDate(date: String?) {
    this?.text = date?.let {
        DateUtil.formatDate(
            it, DateUtil.DATE_TIME_FORMAT_API, DateUtil.DATE_FORMAT_LOCAL
        )
    } ?: run {
        ""
    }
}
//endregion TextView