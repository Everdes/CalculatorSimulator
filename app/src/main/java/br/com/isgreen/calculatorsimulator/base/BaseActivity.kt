package br.com.isgreen.calculatorsimulator.base

import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Éverdes Soares on 02/16/2020.
 */

typealias OnFragmentResult = ((code: Int, data: Any?) -> Unit)?

abstract class BaseActivity : AppCompatActivity() {

    private var mOnFragmentResults = mutableListOf<OnFragmentResult>()

    fun addOnFragmentResult(onFragmentResult: OnFragmentResult) {
        mOnFragmentResults.add(onFragmentResult)
    }

    fun removeOnFragmentResult() {
        mOnFragmentResults.removeAt(mOnFragmentResults.lastIndex)
    }

    fun callOnFragmentResult(code: Int, data: Any? = null) {
        mOnFragmentResults.forEach {
            it?.invoke(code, data)
        }
    }
}