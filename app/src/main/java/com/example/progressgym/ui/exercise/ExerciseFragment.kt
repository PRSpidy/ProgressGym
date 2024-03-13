package com.example.progressgym.ui.exercise

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progressgym.R
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.Muscle
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.RoomExerciseDataSource
import com.example.progressgym.databinding.FragmentCommunBinding
import com.example.progressgym.ui.set.SetFragment
import com.example.progressgym.utils.Resource

private const val ARG_TRAINING_PLAN = "trainingPlan"
private const val ARG_TRAINING = "training"
class ExerciseFragment : Fragment() {

    private lateinit var exerciseAdapter: ExerciseAdapter
    private var _binding: FragmentCommunBinding? = null
    private val binding get() = _binding!!

    private var trainingPlan: TrainingPlan? = null
    private var training: Training? = null

    private lateinit var addExerciseFilterName: String
    private var addExerciseFilerMuscleId: Int = 0

    private lateinit var addExerciseToTrainingAdaper: AddExerciseToTrainingAdaper
    private val roomExercise = RoomExerciseDataSource();
    private val exerciseViewModel: ExerciseViewModel by viewModels {
        ExerciseViewModelFactory(roomExercise)
    }

    private fun onExerciseClickItem(exercise: Exercise) {
        val newFragment = SetFragment()
        val args = Bundle()
        args.putParcelable("trainingPlan", trainingPlan)
        args.putParcelable("training", training)
        args.putParcelable("exercise", exercise)
        newFragment.arguments = args
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
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

        addExerciseToTraining()

        return root
    }

    private fun addExerciseToTraining() {
        exerciseViewModel.addExerciseToTraining.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    exerciseViewModel.getAllExercisesFromTraining(training!!.id)
                }
                Resource.Status.ERROR -> {

                }
                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun newExercise() {
        binding.imageButtonNew.setOnClickListener {
            exerciseViewModel.getAllMuscle()
        }
        exerciseViewModel.allMuscle.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    newExercisePopUp(it.data!!)
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }
        exerciseViewModel.exercises.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    addExerciseToTrainingAdaper.submitList(it.data)
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }

    }

    private fun newExercisePopUp(muscles: List<Muscle>) {

        Log.i("saddas", muscles.toString())
        val noneMuscle = Muscle(id = 0, name = "none")
        val updatedMusclesList = mutableListOf(noneMuscle)
        updatedMusclesList.addAll(muscles)

        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.popup_add_exercise_training, null)

        val spinner = dialogView.findViewById<Spinner>(R.id.spinnerMuscles)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recyclerViewListExercises)
        addExerciseToTrainingAdaper = AddExerciseToTrainingAdaper()
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = addExerciseToTrainingAdaper
        exerciseViewModel.getAllExercises(training!!.id)
        addExerciseFilterName = ""

        //Filter dinamic of spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedMuscle = spinner.selectedItem as Muscle
                addExerciseFilerMuscleId = selectedMuscle.id

                val filteredExercises = if (addExerciseFilerMuscleId != 0) {
                    exerciseViewModel.exercises.value?.data?.filter { exercise ->
                        exercise.name.contains(addExerciseFilterName, ignoreCase = true) &&
                                exercise.muscle.id == addExerciseFilerMuscleId
                    }
                } else {
                    // Si addExerciseFilerMuscleId es igual a 0, no se aplica el filtro por músculo
                    exerciseViewModel.exercises.value?.data?.filter { exercise ->
                        exercise.name.contains(addExerciseFilterName, ignoreCase = true)
                    }
                }

                addExerciseToTrainingAdaper.submitList(filteredExercises)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //Filter dinamic of exercise name
        dialogView.findViewById<TextView>(R.id.editTextExerciseName).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                addExerciseFilterName = charSequence.toString()


                val filteredExercises = if (addExerciseFilerMuscleId != 0) {
                    exerciseViewModel.exercises.value?.data?.filter { exercise ->
                        exercise.name.contains(addExerciseFilterName, ignoreCase = true) &&
                                exercise.muscle.id == addExerciseFilerMuscleId
                    }
                } else {
                    exerciseViewModel.exercises.value?.data?.filter { exercise ->
                        exercise.name.contains(addExerciseFilterName, ignoreCase = true)
                    }
                }
                // Actualizar el adaptador con los resultados filtrados
                addExerciseToTrainingAdaper.submitList(filteredExercises)


            }

            override fun afterTextChanged(editable: Editable?) {}
        })



        // Creamos un adaptador personalizado para el Spinner
        val muscleAdapter = object : ArrayAdapter<Muscle>(requireContext(), android.R.layout.simple_spinner_item, updatedMusclesList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.text = updatedMusclesList[position].name
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.text = updatedMusclesList[position].name
                return view
            }
        }

        muscleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = muscleAdapter

        builder.setView(dialogView)

        builder.setPositiveButton("Aceptar") { _, _ ->

            val lastSelectedItemIndex = addExerciseToTrainingAdaper.getLastSelectedItemIndex()
            if(lastSelectedItemIndex != null){
                exerciseViewModel.addExerciseToTraining(training!!.id, lastSelectedItemIndex.id )
            }
        }

        builder.setNegativeButton("Cancelar") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
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