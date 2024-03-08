package com.example.progressgym.ui.exercise

import android.content.Context
import com.example.progressgym.data.model.Training
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
import com.example.progressgym.R
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.databinding.ItemTrainingBinding
import androidx.fragment.app.Fragment
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.databinding.ItemTrainingPlanBinding
import com.example.progressgym.ui.training.TrainingAdapter
import java.text.SimpleDateFormat
class ExerciseAdapter (
    private val onClickListener: (Exercise) -> Unit,
    private val context: Context
):
ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {


    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseAdapter.ExerciseViewHolder {
        val binding = ItemTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseAdapter.ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
        holder.itemView.setOnClickListener {
            onClickListener(exercise)
        }
    }
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    inner class ExerciseViewHolder(private val binding: ItemTrainingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.TextViewName.text = exercise.name
            binding.textViewAditionalData.text = exercise.muscle.name
            binding.textViewTrainingId.text = exercise.id.toString()
        }
    }

    class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {

        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Exercise,
            newItem: Exercise
        ): Boolean {
            return oldItem == newItem
        }
    }
}