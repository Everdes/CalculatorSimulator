package br.com.isgreen.calculatorsimulator.screen.simulator

import android.content.Context
import br.com.isgreen.calculatorsimulator.R
import br.com.isgreen.calculatorsimulator.base.BaseValidationException
import br.com.isgreen.calculatorsimulator.base.BaseValidatorHelper
import br.com.isgreen.calculatorsimulator.util.DateUtil

/**
 * Created by Éverdes Soares on 02/16/2020.
 */

class SimulatorValidatorHelper(private val context: Context) : BaseValidatorHelper {

    companion object {
        private const val JANUARY = 1
        private const val DECEMBER = 12
        private const val DATE_MAX = "31/12/2100"
    }

    override suspend fun validate(vararg any: Any?) {
        val rate = any[0] as? Int?
        val maturityDate = any[1] as? String?
        val investedAmount = any[2] as? Double?

        when {
            isEmpty(investedAmount) ->
                throw BaseValidationException(context.getString(R.string.type_how_much_you_like_apply))
            isEmpty(maturityDate) ->
                throw BaseValidationException(context.getString(R.string.type_the_maturity_date_investment))
            isInvalid(maturityDate) ->
                throw BaseValidationException(context.getString(R.string.type_valid_the_maturity_date_investment))
            isEmpty(rate) ->
                throw BaseValidationException(context.getString(R.string.type_cdi_investment_percent))
        }
    }

    private fun isEmpty(investedAmount: Double?): Boolean {
        return investedAmount == null || investedAmount == 0.0
    }

    private fun isEmpty(maturityDate: String?): Boolean {
        return maturityDate == null || maturityDate == ""
    }

    private fun isInvalid(maturityDate: String?): Boolean {
        return maturityDate == null
                || DateUtil.isLessThanCurrent(maturityDate)
                || DateUtil.isGreaterThan(maturityDate, DATE_MAX)
                || DateUtil.getMonth(maturityDate) > DECEMBER
                || DateUtil.getMonth(maturityDate) < JANUARY
                || DateUtil.getDay(maturityDate) > 31
                || DateUtil.getDay(maturityDate) < 1
    }

    private fun isEmpty(rate: Int?): Boolean {
        return rate == null
    }
}