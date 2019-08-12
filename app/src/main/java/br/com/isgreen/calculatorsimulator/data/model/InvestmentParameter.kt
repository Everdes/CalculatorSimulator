package br.com.isgreen.calculatorsimulator.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

@Parcelize
data class InvestmentParameter(

    val isTaxFree: Boolean?,
    val maturityDate: String?,
    val maturityTotalDays: Int?,
    val maturityBusinessDays: Int?,

    val rate: Double?,
    val investedAmount: Double?,
    val yearlyInterestRate: Double?

) : Parcelable