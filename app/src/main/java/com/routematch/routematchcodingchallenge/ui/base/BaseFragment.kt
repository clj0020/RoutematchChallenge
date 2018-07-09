package com.routematch.routematchcodingchallenge.ui.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import dagger.android.support.AndroidSupportInjection
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog



/**
 * This is the BaseFragment class that removes some of the boilerplate of binding to data, displaying dialogs, and other helpful things.
 */
abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>>: Fragment() {

    var baseActivity: BaseActivity<*, *>? = null
        private set
    private var mRootView: View? = null
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null

    private lateinit var mSuccessDialog: SweetAlertDialog
    private lateinit var mErrorDialog: SweetAlertDialog

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    init {
    }

    // Check if network is connected.
    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    // When the view is created, perform data binding.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.getRoot()
        return mRootView
    }

    // When the View has been created, bind the viewmodel and execute all other pending bindings.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    // Performs dependency injection.
    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    // Called when fragment is attached to an activity and lets the activity know.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context
            this.baseActivity = activity
            activity.onFragmentAttached()
        }
    }

    // Called when fragment is detached from an activity.
    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    // Hides the keyboard.
    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    // Shows a success dialog.
    fun showSuccess(title: String, content: String) {
        mSuccessDialog = SweetAlertDialog(baseActivity, SweetAlertDialog.SUCCESS_TYPE)
        mSuccessDialog.titleText = title
        mSuccessDialog.contentText = content
        mSuccessDialog.show()
    }

    // Hides the success dialog.
    fun hideSuccess() {
        mSuccessDialog.hide()
    }

    // Shows an error dialog.
    fun showError(title: String, content: String) {
        mErrorDialog = SweetAlertDialog(baseActivity, SweetAlertDialog.ERROR_TYPE)
        mErrorDialog.titleText = title
        mErrorDialog.contentText = content
        mErrorDialog.show()
    }

    // Hides the error dialog.
    fun hideError() {
        mErrorDialog.hide()
    }


    // An interface for hosting activities to receive updates from the fragment.
    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}