package com.example.marti.spacex.ui.launcheslist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.marti.spacex.databinding.LaunchesFragmentBinding
import com.example.marti.spacex.di.Injectable
import com.example.marti.spacex.model.SpaceX
import com.example.marti.spacex.ui.LaunchesInterface
import com.example.marti.spacex.utils.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.marti.spacex.R


class LaunchesFragment : Fragment(), Injectable, LaunchesInterface {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding by autoCleared<LaunchesFragmentBinding>()

    private var mAdapter by autoCleared<LaunchesAdapter>()

    private lateinit var viewModel: LaunchesViewModel

    private var sortingBy = SORTING_BY_ROCKET_ID
    private var launches = LAUNCHES_PAST

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            com.example.marti.spacex.R.id.navigation_upcoming -> {
                launches = LAUNCHES_UPCOMING
                loadData(launches, sortingBy)
                return@OnNavigationItemSelectedListener true
            }
            com.example.marti.spacex.R.id.navigation_past -> {
                launches = LAUNCHES_PAST
                loadData(launches, sortingBy)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, com.example.marti.spacex.R.layout.launches_fragment, container, false
        )
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LaunchesViewModel::class.java)
        mAdapter = LaunchesAdapter(this)
        viewModel.connectToApi()
        val sharePref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        sortingBy = sharePref.getString(SORT_ORDER_KEY, SORTING_BY_ROCKET_ID)
        loadData(launches, sortingBy)
        mBinding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        mBinding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.adapter = mAdapter
        connectToApiError()
    }

    private fun connectToApiError(){
        viewModel.launchesApiConnectError().observe(this, Observer {it ->
            Toast.makeText(
                context, resources.getString(R.string.error_message) + it,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    private fun loadData(launches: String, sortingBy: String) {
        viewModel.launchesFromDatabase(launches, sortingBy).observe(this, Observer { it ->
            if (!it.isEmpty()) {
                mAdapter.setLaunchesList(it)
                mBinding.dataLoad = true
            }
        })
    }

    override fun onClick(spaceX: SpaceX) {
        val args =Bundle()
        args.putInt(LAUNCH_ID, spaceX.flight_number)
        findNavController().navigate(R.id.detailFragment, args)
    }
}
