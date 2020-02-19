package br.com.isgreen.calculatorsimulator.extension

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.isgreen.calculatorsimulator.base.BaseActivity

/**
 * Created by Éverdes Soares on 02/16/2020.
 */

fun Fragment.isAtLeastLollipop(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}

fun Fragment.isAtLeastMarshmallow(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

fun Fragment.isAtLeastNougat(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
}

fun Fragment.isAtLeastQ(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}

val Fragment.baseActivity get() = this.activity as? BaseActivity

val Fragment.appCompatActivity get() = this.activity as? AppCompatActivity

fun Fragment.showMessage(message: Any) {
    when (message) {
        is Int -> {
            context?.toastLong(message)
        }
        is CharSequence -> {
            context?.toastLong(message)
        }
        else -> {
            throw RuntimeException("Message must be Int or CharSequence.")
        }
    }
}