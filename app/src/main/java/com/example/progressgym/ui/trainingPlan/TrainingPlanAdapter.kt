package com.example.progressgym.ui.trainingPlan

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.databinding.ItemTrainingPlanBinding
import java.text.SimpleDateFormat

class TrainingPlanAdapter (
    private val onClickListener: (TrainingPlan) -> Unit,
) :
    ListAdapter<TrainingPlan, TrainingPlanAdapter.TrainingPlanViewHolder>(TrainingPlanDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingPlanViewHolder {
        val binding = ItemTrainingPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingPlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrainingPlanViewHolder, position: Int) {
        val trainingPlan = getItem(position)
        holder.bind(trainingPlan)
        holder.itemView.setOnClickListener {
            onClickListener(trainingPlan)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    inner class TrainingPlanViewHolder(private val binding: ItemTrainingPlanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trainingPlan: TrainingPlan) {
            binding.TextViewTrainingName.text = trainingPlan.name
            binding.textViewTrainingPlanId.text = trainingPlan.id.toString()
        }
    }

    class TrainingPlanDiffCallback : DiffUtil.ItemCallback<TrainingPlan>() {

        override fun areItemsTheSame(oldItem: TrainingPlan, newItem: TrainingPlan): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TrainingPlan,
            newItem: TrainingPlan
        ): Boolean {
            return oldItem == newItem
        }


    }
}