package com.hesham.moviedbtask.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hesham.moviedbtask.R
import com.hesham.moviedbtask.ui.MainActivity
import com.hesham.moviedbtask.util.dismissLLoadingDialog
import com.hesham.moviedbtask.util.showDialog

open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLLoadingDialog()
        showDialog(
            message = message,
            textPositive = getString(R.string.ok)
        )
    }

    fun changeActivityTitle(title: String, showBackButton: Boolean) {
        if (activity is MainActivity) {
            (activity as MainActivity).changeTitle(title, showBackButton)
        }
    }
}
