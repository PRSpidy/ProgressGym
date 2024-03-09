package com.example.progressgym.ui.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.progressgym.R
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.databinding.ItemExerciseBinding
import com.example.progressgym.databinding.PopupAddExerciseTrainingBinding

class AddExerciseToTrainingAdaper():
    ListAdapter<Exercise, AddExerciseToTrainingAdaper.AddExerciseToTrainingViewHolder>(AddExerciseToTrainingDiffCallback()) {

    private var selectedItemIndex: Int = RecyclerView.NO_POSITION
    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddExerciseToTrainingViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddExerciseToTrainingViewHolder(binding)
    }

    fun getLastSelectedItemIndex(): Exercise? {
        return if (selectedItemIndex != RecyclerView.NO_POSITION) {
            getItem(selectedItemIndex)
        } else {
            null
        }
    }

    override fun onBindViewHolder(holder: AddExerciseToTrainingViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)

        val backgroundColor = if (position == selectedItemIndex) {
            ContextCompat.getColor(holder.itemView.context, R.color.grey)
        } else {
            ContextCompat.getColor(holder.itemView.context, R.color.transparent)
        }
        holder.itemView.setBackgroundColor(backgroundColor)

        holder.itemView.setOnClickListener {
            exercise.isSelected = true

            if (selectedItemIndex != RecyclerView.NO_POSITION) {
                notifyItemChanged(selectedItemIndex)
                getItem(selectedItemIndex).isSelected = false
            }
            selectedItemIndex = holder.adapterPosition

            notifyItemChanged(holder.adapterPosition)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    inner class AddExerciseToTrainingViewHolder(private val binding: ItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.itemTextViewExerciseName.text = exercise.name
            binding.idTextViewIdExercise.text = exercise.id.toString()
            binding.idTextViewIdMuscle.text = exercise.muscle.id.toString()
        }
    }


    class AddExerciseToTrainingDiffCallback : DiffUtil.ItemCallback<Exercise>() {

        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            // TODO
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Exercise,
            newItem: Exercise
        ): Boolean {
            // TODO
            return oldItem == newItem
        }

    }

}