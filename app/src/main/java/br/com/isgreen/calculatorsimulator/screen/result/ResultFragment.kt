package br.com.isgreen.calculatorsimulator.screen.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.isgreen.calculatorsimulator.R
import br.com.isgreen.calculatorsimulator.data.model.Simulation
import br.com.isgreen.calculatorsimulator.util.extension.setCurrency
import br.com.isgreen.calculatorsimulator.util.extension.setCurrencyAndPercent
import br.com.isgreen.calculatorsimulator.util.extension.setDate
import br.com.isgreen.calculatorsimulator.util.extension.setPercent
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.android.synthetic.main.fragment_result_header.*
import kotlinx.android.synthetic.main.fragment_result_percentage.*
import kotlinx.android.synthetic.main.fragment_result_value.*
import kotlinx.android.synthetic.main.fragment_result_value.txtIncomeValue

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

class ResultFragment : Fragment() {

    companion object {
        const val ARG_SIMULATION = "simulation"
    }

    //region Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }
    //endregion Fragment

    //region Local
    private fun initView() {
        setDataInView()

        btnSimulateAgain.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setDataInView() {
        val simulation = arguments?.getParcelable<Simulation>(ARG_SIMULATION)

        simulation?.let {
            txtValue?.setCurrency(it.grossAmount)
            txtTotalIncomeValue?.setCurrency(it.investmentParameter?.yearlyInterestRate)

            txtInitiallyAppliedValue?.setCurrency(it.investmentParameter?.investedAmount)
            txtGrossInvestmentValue?.setCurrency(it.grossAmount)
            txtIncomeValue?.setCurrency(it.investmentParameter?.yearlyInterestRate)
            txtIRInvestmentValue?.setCurrencyAndPercent(it.investmentParameter?.yearlyInterestRate, it.taxesRate)
            txtNetInvestmentValue?.setCurrency(it.netAmount)

            txtRescueDateValue?.setDate(it.investmentParameter?.maturityDate)
            txtCalendarDaysValue?.text = it.investmentParameter?.maturityTotalDays.toString()
            txtMonthlyIncomeValue?.setPercent(it.monthlyGrossRateProfit)
            txtInvestmentCDIPercentageValue?.setPercent(it.investmentParameter?.rate)
            txtAnnualProfitabilityValue?.setPercent(it.annualNetRateProfit)
            txtPeriodProfitabilityValue?.setPercent(it.rateProfit)
        }
    }
    //region Local
}