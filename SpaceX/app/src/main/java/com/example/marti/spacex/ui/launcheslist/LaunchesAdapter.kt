package com.example.marti.spacex.ui.launcheslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marti.spacex.databinding.LaunchesListBinding
import com.example.marti.spacex.model.SpaceX
import com.example.marti.spacex.ui.LaunchesInterface

class LaunchesAdapter(
    private val mClickListener: LaunchesInterface
) : RecyclerView.Adapter<LaunchesAdapter.ViewHolder>() {

    private lateinit var mLaunches: List<SpaceX>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LaunchesListBinding.inflate(inflater)
        binding.click = mClickListener
        return ViewHolder(binding)
    }

    fun setLaunchesList(launchesList: List<SpaceX>){
        mLaunches = launchesList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mLaunches.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launches = mLaunches[position]
        holder.bind(launches)
    }

    inner class ViewHolder(private val binding: LaunchesListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(spaceX: SpaceX) {
            binding.launches = spaceX
            binding.executePendingBindings()
        }
    }
}