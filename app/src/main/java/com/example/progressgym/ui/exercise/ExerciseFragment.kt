package com.example.progressgym.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.progressgym.R
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.MuscleSpinner
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.RoomExerciseDataSource
import com.example.progressgym.data.repository.local.RoomTrainingDataSource
import com.example.progressgym.databinding.FragmentCommunBinding
import com.example.progressgym.ui.training.TrainingAdapter
import com.example.progressgym.ui.training.TrainingViewModel
import com.example.progressgym.ui.training.TrainingViewModelFactory
import com.example.progressgym.utils.Resource

private const val ARG_TRAINING_PLAN = "trainingPlan"
private const val ARG_TRAINING = "training"
class ExerciseFragment : Fragment() {

    private lateinit var exerciseAdapter: ExerciseAdapter
    private var _binding: FragmentCommunBinding? = null
    private val binding get() = _binding!!

    private var trainingPlan: TrainingPlan? = null
    private var training: Training? = null

    private val roomExercise = RoomExerciseDataSource();
    private val exerciseViewModel: ExerciseViewModel by viewModels {
        ExerciseViewModelFactory(roomExercise)
    }

    private fun onExerciseClickItem(exercise: Exercise) {
        /*val newFragment = ExerciseFragment()
        val args = Bundle()
        args.putParcelable("trainingPlan", trainingPlan)
        args.putParcelable("training", training)
        newFragment.arguments = args
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            trainingPlan = it.getParcelable(ARG_TRAINING_PLAN, TrainingPlan::class.java)
            training = it.getParcelable(ARG_TRAINING, Training::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunBinding.inflate(inflater, container, false)
        val root: View = binding.root
        exerciseAdapter = ExerciseAdapter (::onExerciseClickItem, requireContext())
        binding.list.adapter = exerciseAdapter
        binding.textViewNameOfFragment.text = getString(R.string.exercises)
        exerciseViewModel.getAllExercisesFromTraining(training!!.id)

        showExercises()

        newExercise()

        return root
    }

    private fun newExercise() {
        binding.imageButtonNew.setOnClickListener {
            exerciseViewModel.getAllExercisesOfEachMuscle()
        }
        exerciseViewModel.exercisesByMuscle.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    newExercisePopUp(it.data)
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }


    }

    private fun newExercisePopUp(data: List<MuscleSpinner>?) {
        //TODO falta mostrar el pop up al crear
    }

    private fun showExercises() {
        exerciseViewModel.items.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    exerciseAdapter.submitList(it.data)
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }
    }

}