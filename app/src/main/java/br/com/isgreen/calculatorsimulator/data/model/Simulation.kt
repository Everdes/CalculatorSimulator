package br.com.isgreen.calculatorsimulator.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

@Parcelize
data class Simulation(

    val taxesRate: Double?,
    val netAmount: Double?,
    val rateProfit: Double?,
    val grossAmount: Double?,
    val taxesAmount: Double?,
    val netAmountProfit: Double?,
    val grossAmountProfit: Double?,
    val annualNetRateProfit: Double?,
    val dailyGrossRateProfit: Double?,
    val annualGrossRateProfit: Double?,
    val monthlyGrossRateProfit: Double?,
    val investmentParameter: InvestmentParameter?

) : Parcelable