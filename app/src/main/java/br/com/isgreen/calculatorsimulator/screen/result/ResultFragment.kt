package br.com.isgreen.calculatorsimulator.screen.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.isgreen.calculatorsimulator.R
import br.com.isgreen.calculatorsimulator.base.BaseContract
import br.com.isgreen.calculatorsimulator.base.BaseFragment
import br.com.isgreen.calculatorsimulator.extension.setCurrency
import br.com.isgreen.calculatorsimulator.extension.setCurrencyAndPercent
import br.com.isgreen.calculatorsimulator.extension.setDate
import br.com.isgreen.calculatorsimulator.extension.setPercent
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.android.synthetic.main.fragment_result_header.*
import kotlinx.android.synthetic.main.fragment_result_percentage.*
import kotlinx.android.synthetic.main.fragment_result_value.*

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

class ResultFragment : BaseFragment() {

    //region Base Fragment
    override fun layout() = R.layout.fragment_result

    override fun getViewModel(): BaseContract.ViewModel? = null

    override fun initView() {
        setDataInView()

        btnSimulateAgain.setOnClickListener {
            popBackStack()
        }
    }

    override fun changeLoading(isLoading: Boolean) {}

    override fun showError(message: Any) {}
    //endregion Base Fragment

    //region Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }
    //endregion Fragment

    //region Local
    private fun setDataInView() {
        val simulation = arguments?.let { args -> ResultFragmentArgs.fromBundle(args).simulation }

        simulation?.let {
            txtValue?.setCurrency(it.grossAmount)
            txtTotalIncomeValue?.setCurrency(it.investmentParameter?.yearlyInterestRate)

            txtInitiallyAppliedValue?.setCurrency(it.investmentParameter?.investedAmount)
            txtGrossInvestmentValue?.setCurrency(it.grossAmount)
            txtIncomeValue?.setCurrency(it.investmentParameter?.yearlyInterestRate)
            txtIRInvestmentValue?.setCurrencyAndPercent(
                it.investmentParameter?.yearlyInterestRate,
                it.taxesRate
            )
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