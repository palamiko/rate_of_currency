package com.example.rateofcurrency.ui.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

open class BaseFragment(layout: Int) : Fragment(layout) {

    var coroutineScope = createCoroutineScope()

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun createCoroutineScope() = CoroutineScope(Job() + Dispatchers.IO)

    override fun onDetach() {
        coroutineScope.cancel("It's time")

        super.onDetach()
    }


}