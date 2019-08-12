package br.com.isgreen.calculatorsimulator.validation

import br.com.isgreen.calculatorsimulator.R
import br.com.isgreen.calculatorsimulator.util.DateUtil

/**
 * Created by Éverdes Soares on 08/10/2019
 */

class SimulationValidator {

    companion object {
        private const val JANUARY = 1
        private const val DECEMBER = 12
        private const val DATE_MAX = "31/12/2100"

        const val NO_ERROR = -1

        fun validate(rate: Int?, maturityDate: String?, investedAmount: Double?): Int {
            return when {
                isEmpty(investedAmount) ->
                    R.string.type_how_much_you_like_apply
                isEmpty(maturityDate) ->
                    R.string.type_the_maturity_date_investment
                isInvalid(maturityDate) ->
                    R.string.type_valid_the_maturity_date_investment
                isEmpty(rate) ->
                    R.string.type_cdi_investment_percent
                else -> NO_ERROR
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

}