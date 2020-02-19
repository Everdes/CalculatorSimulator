package br.com.isgreen.calculatorsimulator.screen.simulator

import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import br.com.isgreen.calculatorsimulator.R
import br.com.isgreen.calculatorsimulator.base.BaseFragment
import br.com.isgreen.calculatorsimulator.data.model.Simulation
import br.com.isgreen.calculatorsimulator.extension.hideKeyboard
import br.com.isgreen.calculatorsimulator.extension.showMessage
import kotlinx.android.synthetic.main.fragment_simulator.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

class SimulatorFragment : BaseFragment() {

    private val mViewModel: SimulatorViewModel by viewModel()

    //region Base Fragment
    override fun getViewModel() = mViewModel

    override fun layout() = R.layout.fragment_simulator

    override fun initView() {
        btnSimulate?.setOnClickListener { getSimulation() }

        edtRatePercentage?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getSimulation()

                true
            } else {
                false
            }
        }
    }

    override fun initObservers() {
        mViewModel.simulation.observe(this, Observer { simulation ->
            showResult(simulation)
        })
    }

    override fun changeLoading(isLoading: Boolean) {
        btnSimulate?.hideKeyboard()
        pbLoad?.isVisible = isLoading
        btnSimulate?.isEnabled = !isLoading
        edtMaturityDate?.isEnabled = !isLoading
        edtInvestedAmount?.isEnabled = !isLoading
        edtRatePercentage?.isEnabled = !isLoading
    }

    override fun showError(message: Any) {
        showMessage(message)
    }
    //endregion Base Fragment

    //region Local
    private fun getSimulation() {
        mViewModel.getSimulation(
            edtRatePercentage?.text?.toString()?.toIntOrNull(),
            edtMaturityDate?.text?.toString(),
            edtInvestedAmount?.getValue()
        )
    }

    private fun showResult(simulation: Simulation) {
        val directions = SimulatorFragmentDirections.actionSimulatorFragmentToResultFragment(simulation)
        navigate(directions)
    }
    //region Local
}