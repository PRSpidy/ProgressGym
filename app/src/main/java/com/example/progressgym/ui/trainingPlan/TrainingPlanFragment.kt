package com.example.progressgym.ui.trainingPlan

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.progressgym.R
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.RoomTrainingPlanDataSource
import com.example.progressgym.databinding.FragmentCommunBinding
import com.example.progressgym.ui.training.TrainingFragment
import com.example.progressgym.utils.Resource

class TrainingPlanFragment : Fragment() {

    private var _binding: FragmentCommunBinding? = null
    private val binding get() = _binding!!
    private lateinit var trainingPlanAdapter: TrainingPlanAdapter
    private val roomTrainingPlan = RoomTrainingPlanDataSource();

    private val trainingPlanViewModel: TrainingPlanViewModel by viewModels {
        TrainingPlanViewModelFactory(roomTrainingPlan)
    }

    private fun onTrainingPlanClickItem(trainingPlan: TrainingPlan) {
        val newFragment = TrainingFragment()
        val args = Bundle()
        args.putParcelable("trainingPlan", trainingPlan)
        newFragment.arguments = args
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunBinding.inflate(inflater, container, false)
        val root: View = binding.root
        trainingPlanAdapter = TrainingPlanAdapter (::onTrainingPlanClickItem)
        binding.list.adapter = trainingPlanAdapter
        binding.textViewNameOfFragment.text = getString(R.string.training_plans)

        newTrainingPlan()

        showTrainingPlans()

        newTrainingPlanSavedInRoom()

        return root
    }

    private fun newTrainingPlanSavedInRoom() {
        trainingPlanViewModel.created.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    trainingPlanViewModel.getTrainingPlans()
                }
                Resource.Status.ERROR -> {

                }
                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun showTrainingPlans() {
        trainingPlanViewModel.items.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    trainingPlanAdapter.submitList(it.data)
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun newTrainingPlan() {
        binding.imageButtonNew.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.popup_new_training_plan, null)
            builder.setView(dialogView)
            builder.setPositiveButton("Aceptar") { _, _ ->
                val newTrainingPlanName = dialogView.findViewById<EditText>(R.id.editTextExerciseName).text.toString()

                trainingPlanViewModel.addNewTrainingPlan(newTrainingPlanName)
            }
            builder.setNegativeButton("Cancelar") { _, _ ->
            }
            val dialog = builder.create()
            dialog.show()
        }
    }


}