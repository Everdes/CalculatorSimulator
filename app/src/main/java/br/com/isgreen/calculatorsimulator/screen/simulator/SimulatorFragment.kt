package br.com.isgreen.calculatorsimulator.screen.simulator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.isgreen.calculatorsimulator.R
import br.com.isgreen.calculatorsimulator.data.model.Simulation
import br.com.isgreen.calculatorsimulator.screen.result.ResultFragment
import br.com.isgreen.calculatorsimulator.util.extension.hideKeyboard
import br.com.isgreen.calculatorsimulator.util.extension.toastShort
import kotlinx.android.synthetic.main.fragment_simulator.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Éverdes Soares on 08/07/2019.
 */

class SimulatorFragment : Fragment(), SimulatorContract.View {

    private val mViewModel: SimulatorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simulator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }
    //endregion Fragment

    //region Local
    private fun initObservers() {
        mViewModel.loading.observe(this, Observer { loading ->
            changeViewsState(loading)
            hideKeyboard()
        })

        mViewModel.message.observe(this, Observer { message ->
            showMessage(message)
        })

        mViewModel.simulation.observe(this, Observer { simulation ->
            showResult(simulation)
        })
    }

    private fun initView() {
        btnSimulate.setOnClickListener {
            getSimulation()
        }

        edtRatePercentage.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getSimulation()

                true
            } else {
                false
            }
        }
    }

    private fun hideKeyboard() {
        btnSimulate?.hideKeyboard()
    }

    private fun showMessage(@StringRes message: Int) {
        context?.toastShort(message)
    }

    private fun getSimulation() {
        mViewModel.getSimulation(
            edtRatePercentage?.text?.toString()?.toIntOrNull(),
            edtMaturityDate?.text?.toString(),
            edtInvestedAmount?.getValue()
        )
    }
    //region Local

    //region Contract
    override fun changeViewsState(loading: Boolean) {
        pbLoad?.isVisible = loading
        btnSimulate?.isEnabled = !loading
        edtMaturityDate?.isEnabled = !loading
        edtInvestedAmount?.isEnabled = !loading
        edtRatePercentage?.isEnabled = !loading
    }

    override fun showResult(simulation: Simulation) {
        val bundle = bundleOf(ResultFragment.ARG_SIMULATION to simulation)
        findNavController().navigate(R.id.action_simulatorFragment_to_resultFragment, bundle)
    }
    //endregion Contract
}