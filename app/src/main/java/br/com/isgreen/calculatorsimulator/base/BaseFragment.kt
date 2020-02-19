package br.com.isgreen.calculatorsimulator.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import br.com.isgreen.calculatorsimulator.R
import br.com.isgreen.calculatorsimulator.extension.baseActivity
import br.com.isgreen.calculatorsimulator.extension.hideKeyboard
import br.com.isgreen.calculatorsimulator.extension.isAtLeastLollipop
import br.com.isgreen.calculatorsimulator.extension.isAtLeastMarshmallow

/**
 * Created by Éverdes Soares on 02/16/2020.
 */

abstract class BaseFragment : Fragment() {

    enum class TransitionAnimation {
        TRANSLATE_FROM_RIGHT,
        TRANSLATE_FROM_LEFT,
        TRANSLATE_FROM_DOWN,
        TRANSLATE_FROM_UP,
        FADE
    }

    //region Abstracts
    @LayoutRes
    abstract fun layout(): Int
    abstract fun initView()
    abstract fun showError(message: Any)
    abstract fun changeLoading(isLoading: Boolean)
    abstract fun getViewModel(): BaseContract.ViewModel?
    //endregion Abstracts

    //region Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseActivity?.addOnFragmentResult { code, data -> onFragmentResult(code, data) }

        setHasOptionsMenu(true)
        setDefaultStatusBarColor()
        initDefaultObservers()
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout(), container, false)
        view?.hideKeyboard()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            popBackStack()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        baseActivity?.removeOnFragmentResult()
    }
    //endregion Fragment

    //region Local
    private fun initDefaultObservers() {
        val baseViewModel = getViewModel()

        baseViewModel?.destination?.observe(this, Observer { destination ->
            navigate(destination, TransitionAnimation.FADE)
        })

        baseViewModel?.loading?.observe(this, Observer { isLoading ->
            changeLoading(isLoading)
        })

        baseViewModel?.errorMessage?.observe(this, Observer { errorMessage ->
            showError(errorMessage)
        })
    }

    open fun initObservers() {}
    //endregion Local

    //region Fragment Result
    open fun onFragmentResult(code: Int, data: Any?) {}

    protected fun setResult(code: Int, data: Any? = null) {
        baseActivity?.callOnFragmentResult(code, data)
    }
    //endregion Fragment Result

    //region StatusBar
    private fun setDefaultStatusBarColor() {
        val themeStatusColor = if (isAtLeastMarshmallow()) getThemeStatusColor() else R.color.gray
        changeStatusBarColor(themeStatusColor)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getThemeStatusColor(): Int {
        val outTypedValue = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorPrimaryDark, outTypedValue, true)
        return outTypedValue.data
    }

    protected fun changeStatusBarColor(color: Int) {
        activity?.let {
            if (isAtLeastMarshmallow()) {
                it.window.statusBarColor = color
                changeStatusBarIconsColor(color != Color.WHITE)
            } else if (isAtLeastLollipop()) {
                it.window.statusBarColor = color
            }
        }
    }

    protected fun changeStatusBarIconsColor(toWhite: Boolean) {
        activity?.let {
            it.window.decorView.systemUiVisibility = if (
                toWhite || Build.VERSION.SDK_INT < Build.VERSION_CODES.M
            ) {
                0
            } else {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
    //endregion StatusBar

    //region Navigation
    fun navigate(
        directions: NavDirections,
        popUpTo: Int? = null,
        clearBackStack: Boolean? = false
    ) {
        val navController = NavHostFragment.findNavController(this)
        val destinationId = if (clearBackStack == true) navController.graph.id else popUpTo
        val options =
            buildOptions(TransitionAnimation.TRANSLATE_FROM_RIGHT, clearBackStack, destinationId)

        navController.navigate(directions, options)
    }

    fun navigate(
        directions: NavDirections,
        animation: TransitionAnimation? = TransitionAnimation.TRANSLATE_FROM_RIGHT,
        popUpToDestinationId: Int? = null,
        clearBackStack: Boolean? = false
    ) {
        val navController = NavHostFragment.findNavController(this)
        val destinationId =
            if (clearBackStack == true) navController.graph.id else popUpToDestinationId
        val options = buildOptions(animation, clearBackStack, destinationId)

        navController.navigate(directions, options)
    }

    fun navigate(
        @IdRes resId: Int,
        clearBackStack: Boolean? = false,
        popUpToDestinationId: Int? = null,
        animation: TransitionAnimation? = TransitionAnimation.TRANSLATE_FROM_RIGHT
    ) {
        navigate(resId, animation, null, popUpToDestinationId, clearBackStack)
    }

    fun navigate(
        @IdRes resId: Int,
        animation: TransitionAnimation? = TransitionAnimation.TRANSLATE_FROM_RIGHT,
        bundle: Bundle? = null,
        popUpToDestinationId: Int? = null,
        clearBackStack: Boolean? = false
    ) {
        val navController = NavHostFragment.findNavController(this)
        val destinationId =
            if (clearBackStack == true) navController.graph.id else popUpToDestinationId
        val options = buildOptions(animation, clearBackStack, destinationId)

        navController.navigate(resId, bundle, options)
    }

    fun navigate(
        view: View,
        @IdRes resId: Int,
        animation: TransitionAnimation? = TransitionAnimation.TRANSLATE_FROM_RIGHT,
        clearBackStack: Boolean? = false
    ) {
        val navController = Navigation.findNavController(view)
        val destinationId = if (clearBackStack == true) navController.graph.id else null
        val options = buildOptions(animation, clearBackStack, destinationId)

        navController.navigate(resId, null, options)
    }

    fun popBackStack() {
        findNavController().popBackStack()
    }

    fun popUpTo(@IdRes destinationId: Int) {
        findNavController().popBackStack(destinationId, false)
    }

    fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun buildOptions(
        transitionAnimation: TransitionAnimation?,
        clearBackStack: Boolean?,
        @IdRes destinationId: Int?
    ): NavOptions {
        return navOptions {
            anim {
                when (transitionAnimation) {
                    TransitionAnimation.TRANSLATE_FROM_DOWN -> {
                        enter = R.anim.translate_slide_bottom_up
                        exit = R.anim.translate_no_change
                        popEnter = R.anim.translate_no_change
                        popExit = R.anim.translate_slide_bottom_down
                    }
                    TransitionAnimation.TRANSLATE_FROM_LEFT -> {
                        enter = R.anim.translate_right_enter
                        exit = R.anim.translate_right_exit
                        popEnter = R.anim.translate_left_enter
                        popExit = R.anim.translate_left_exit
                    }
                    TransitionAnimation.TRANSLATE_FROM_UP -> {
                        enter = R.anim.translate_slide_bottom_down
                        exit = R.anim.translate_no_change
                        popEnter = R.anim.translate_no_change
                        popExit = R.anim.translate_slide_bottom_up
                    }
                    TransitionAnimation.FADE -> {
                        enter = R.anim.translate_fade_in
                        exit = R.anim.translate_fade_out
                        popEnter = R.anim.translate_fade_in
                        popExit = R.anim.translate_fade_out
                    }
                    else -> {
                        enter = R.anim.translate_left_enter
                        exit = R.anim.translate_left_exit
                        popEnter = R.anim.translate_right_enter
                        popExit = R.anim.translate_right_exit
                    }
                }
            }

            // Para limpar a pilha abaixo do fragment atual,
            // deve-se setar o 'destinationId' = navGraphId e 'inclusive' = true
            destinationId?.let {
                popUpTo(destinationId) {
                    inclusive = clearBackStack == true
                }
            }
        }
    }
    //endregion Navigation
}