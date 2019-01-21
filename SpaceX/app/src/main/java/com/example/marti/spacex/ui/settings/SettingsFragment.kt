package com.example.marti.spacex.ui.settings


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.marti.spacex.R
import com.example.marti.spacex.databinding.FragmentSettings1Binding
import com.example.marti.spacex.di.Injectable
import com.example.marti.spacex.ui.OnClickHandlerInterface
import com.example.marti.spacex.utils.SORTING_BY_ROCKET_ID
import com.example.marti.spacex.utils.SORTING_BY_YEAR
import com.example.marti.spacex.utils.SORT_ORDER_KEY
import com.example.marti.spacex.utils.autoCleared

class SettingsFragment : Fragment(),Injectable, OnClickHandlerInterface {

    private var mBinding by autoCleared<FragmentSettings1Binding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings1, container, false
        )
        mBinding.clickHandler = this
        return mBinding.root
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.tx_sorting_by_rocket-> {
                sharedPreferencesSave(SORTING_BY_ROCKET_ID)
                Toast.makeText(context, "Order by rocket", Toast.LENGTH_SHORT).show()
            }
            R.id.tx_sorting_by_year -> {
                sharedPreferencesSave(SORTING_BY_YEAR)
                Toast.makeText(context, "Order by year", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sharedPreferencesSave(sortOrder: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(SORT_ORDER_KEY, sortOrder)
            apply()
        }
    }
}
