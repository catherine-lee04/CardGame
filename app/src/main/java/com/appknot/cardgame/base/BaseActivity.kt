package com.appknot.bentleymembership.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.appknot.cardgame.R
import com.appknot.core_rx.base.RxBaseActivity

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : RxBaseActivity<VB, VM>() {
    open var isBackButtonVisible: Boolean = false
    private val loadingIndicatorList = ArrayList<AlertDialog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.run {
            // Loading Indicator 표시 여부
            isLoading.observe(this@BaseActivity, Observer {
                if (it) {
                    showLoadingIndicator()
                } else {
                    hideLoadingIndicator()
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (isBackButtonVisible) {
                    onBackPressed()
                } else {
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Loading Indicator 보이기
     */
    fun showLoadingIndicator() {
        loadingIndicatorList.add(
            AlertDialog.Builder(this)
                .setView(View.inflate(this, R.layout.dialog_loading_indicator, null))
                .setCancelable(false)
                .create().apply {
                    window?.setBackgroundDrawableResource(android.R.color.transparent)
                    window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                    window?.setDimAmount(0F)
                    show()
                }
        )
    }

    /**
     * Loading Indicator 가리기
     */
    fun hideLoadingIndicator() {
        loadingIndicatorList.forEach { it.dismiss() }
        loadingIndicatorList.clear()
    }

    companion object {
        const val KEY_INTENT_COLUMNS = "COLUMNS"
        const val KEY_INTENT_ROWS = "ROWS"
    }
}