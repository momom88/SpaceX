package com.example.marti.spacex.ui.detaillaunches


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer

import com.example.marti.spacex.R
import com.example.marti.spacex.databinding.FragmentDetailBinding
import com.example.marti.spacex.di.Injectable
import com.example.marti.spacex.utils.LAUNCH_ID
import com.example.marti.spacex.utils.autoCleared
import javax.inject.Inject

class DetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding by autoCleared<FragmentDetailBinding>()

    private lateinit var viewModel: DetailViewModel

    private var launchId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        launchId = arguments!!.getInt(LAUNCH_ID)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        setupViewModel(launchId)
    }

    private fun setupViewModel(launchId: Int){
        viewModel.getLaunch(launchId).observe(this, Observer{it ->
            if (it != null){
                mBinding.launches = it
            }
        })
    }
}
