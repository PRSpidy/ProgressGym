package com.example.progressgym.ui.training

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.progressgym.R
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.databinding.ItemTrainingBinding
import androidx.fragment.app.Fragment
import com.example.progressgym.databinding.ItemTrainingPlanBinding
import java.text.SimpleDateFormat

class TrainingAdapter (
    private val onClickListener: (Training) -> Unit,
    private val context: Context
) :
    ListAdapter<Training, TrainingAdapter.TrainingViewHolder>(TrainingDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val binding = ItemTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val training = getItem(position)
        holder.bind(training)
        holder.itemView.setOnClickListener {
            onClickListener(training)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    inner class TrainingViewHolder(private val binding: ItemTrainingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(training: Training) {
            binding.TextViewTrainingName.text = training.name
            val dayOfWeek = getDayOfWeek(training.dayEnum);

            binding.textViewDayOfTheTraining.text = dayOfWeek
            binding.textViewTrainingId.text = training.id.toString()
        }
    }

    class TrainingDiffCallback : DiffUtil.ItemCallback<Training>() {

        override fun areItemsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Training,
            newItem: Training
        ): Boolean {
            return oldItem == newItem
        }
    }

    private fun getDayOfWeek(dayEnum: DayEnum): String {
        when (dayEnum) {
            DayEnum.MONDAY -> {
                return context.getString(R.string.monday)
            }
            DayEnum.TUESDAY -> {
                return context.getString(R.string.tuesday)
            }
            DayEnum.WEDNESDAY -> {
                return context.getString(R.string.wednesday)
            }
            DayEnum.THURSDAY -> {
                return context.getString(R.string.thursday)
            }
            DayEnum.FRIDAY -> {
                return context.getString(R.string.friday)
            }
            DayEnum.SATURDAY -> {
                return context.getString(R.string.saturday)
            }
            DayEnum.SUNDAY -> {
                return context.getString(R.string.sunday)
            }
        }
    }
}