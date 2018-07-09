package com.routematch.routematchcodingchallenge.ui.base

import android.os.Build
import android.annotation.TargetApi
import dagger.android.AndroidInjection
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.routematch.routematchcodingchallenge.util.NetworkUtils

/**
 * This is the BaseActivity class that removes some of the boilerplate of binding to data, displaying dialogs, and other helpful things.
 */
abstract class BaseActivity<T: ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseFragment.Callback {

    private lateinit var mLoadingDialog: SweetAlertDialog
    private lateinit var mSuccessDialog: SweetAlertDialog
    private lateinit var mErrorDialog: SweetAlertDialog

    var viewDataBinding: T ?= null
        private set
    private var mViewModel: V ?= null

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

    // For checking network state.
    val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkConnected(applicationContext)

    // Called when a fragment is attached. Not very useful in this context.
    override fun onFragmentAttached() {

    }

    // Called when a fragment is detached.
    override fun onFragmentDetached(tag: String) {

    }

    // Allows for access to the context.
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
    }


    // Performs dependency injection for the Activity.
    fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    // Performs data binding.
    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
    }

    // Checks for permissions.
    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    // Requests permissions safely.
    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    // Shows a loading dialog.
    fun showLoading() {
        mLoadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        mLoadingDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"))
        mLoadingDialog.setTitleText("Loading")
        mLoadingDialog.setCancelable(false)
        mLoadingDialog.show()
    }

    // Hides the loading dialog.
    fun hideLoading() {
        mLoadingDialog.hide()
    }

    // Shows a success dialog.
    fun showSuccess(title: String, content: String) {
        mSuccessDialog = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
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
        mErrorDialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        mErrorDialog.titleText = title
        mErrorDialog.contentText = content
        mErrorDialog.show()
    }

    // Hides the error dialog.
    fun hideError() {
        mErrorDialog.hide()
    }

    // Hides the keyboard.
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}