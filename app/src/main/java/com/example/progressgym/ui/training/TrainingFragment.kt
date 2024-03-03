package com.example.progressgym.ui.training

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.progressgym.R
import com.example.progressgym.data.model.Day
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.RoomTrainingDataSource
import com.example.progressgym.data.repository.local.tables.DayEnum
import com.example.progressgym.databinding.FragmentCommunBinding
import com.example.progressgym.utils.Resource

private const val ARG_CHAT = "trainingPlan"
class TrainingFragment : Fragment() {

    private lateinit var trainingAdapter: TrainingAdapter
    private var _binding: FragmentCommunBinding? = null
    private val binding get() = _binding!!
    private val roomTraining = RoomTrainingDataSource();

    private var trainingPlan: TrainingPlan? = null
    private val trainingViewModel: TrainingViewModel by viewModels {
        TrainingViewModelFactory(roomTraining)
    }
    private fun onTrainingClickItem(training: Training) {
        /*val newFragment = DashboardFragment()
        val args = Bundle()
        args.putParcelable("chat", chat)
        newFragment.arguments = args
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_main, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()*/

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            trainingPlan = it.getParcelable(ARG_CHAT, TrainingPlan::class.java)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunBinding.inflate(inflater, container, false)
        val root: View = binding.root
        trainingAdapter = TrainingAdapter (::onTrainingClickItem, requireContext())
        binding.list.adapter = trainingAdapter
        binding.textViewNameOfFragment.text = getString(R.string.trainings)

        trainingViewModel.getTrainings(trainingPlan!!.id)

        newTraining()

        newTrainingCreated()

        showTrainings()

        return root
    }

    private fun showTrainings() {
        trainingViewModel.items.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    trainingAdapter.submitList(it.data)
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun newTrainingCreated() {
        trainingViewModel.created.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    trainingViewModel.getTrainings(trainingPlan!!.id)
                }
                Resource.Status.ERROR -> {

                }
                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun newTraining() {
        binding.imageButtonNew.setOnClickListener {
            trainingViewModel.getFreeDays(trainingPlan!!.id)
        }

        trainingViewModel.freeDays.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    newTrainingPopUp(it.data)
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun newTrainingPopUp(listOfDays: List<DayEnum>?) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.popup_new_training, null)

        val spinner = dialogView.findViewById<Spinner>(R.id.spinnerDayOfWeek)

        val allDays = DayEnum.entries

        builder.setView(dialogView)
        listOfDays?.let { filteredDays ->
            val remainingDays = allDays.filter { day -> day !in filteredDays }
            val localizedDays = remainingDays.map { getDayOfWeek(it) }

            val dayPairs = remainingDays.mapIndexed { index, dayEnum ->
                Day(getDayOfWeek(dayEnum), dayEnum)
            }

            val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, dayPairs.map { it.name })
            adapter.setDropDownViewResource(R.layout.spinner_item)
            spinner.adapter = adapter
            builder.setPositiveButton("Aceptar") { _, _ ->
                val newTrainingPlanName = dialogView.findViewById<EditText>(R.id.editTextTrainingPlanName).text.toString()
                val selectedDayName = spinner.selectedItem as String
                val selectedDay = dayPairs.first { it.name == selectedDayName }
                val selectedDayEnum = selectedDay.day
                val training = Training(0, newTrainingPlanName, selectedDayEnum)
                trainingViewModel.addNewTraining(training, trainingPlan!!.id)
            }
        }

        builder.setNegativeButton("Cancelar") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun getDayOfWeek(dayEnum: DayEnum): String {
        when (dayEnum) {
            DayEnum.MONDAY -> {
                return getString(R.string.monday)
            }
            DayEnum.TUESDAY -> {
                return getString(R.string.tuesday)
            }
            DayEnum.WEDNESDAY -> {
                return getString(R.string.wednesday)
            }
            DayEnum.THURSDAY -> {
                return getString(R.string.thursday)
            }
            DayEnum.FRIDAY -> {
                return getString(R.string.friday)
            }
            DayEnum.SATURDAY -> {
                return getString(R.string.saturday)
            }
            DayEnum.SUNDAY -> {
                return getString(R.string.sunday)
            }
        }
    }

}