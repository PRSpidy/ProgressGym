package com.example.progressgym.ui.set

import SetAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.progressgym.R
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.Set
import com.example.progressgym.data.model.TablaItem
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.RoomSetDataSource
import com.example.progressgym.databinding.FragmentCommunBinding
import com.example.progressgym.databinding.FragmentSetBinding
import com.example.progressgym.utils.Resource
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


private const val ARG_TRAINING_PLAN = "trainingPlan"
private const val ARG_TRAINING = "training"
private const val ARG_EXERCISE = "exercise"
class SetFragment : Fragment() {

    private var trainingPlan: TrainingPlan? = null
    private var training: Training? = null
    private var exercise: Exercise? = null
    private var _binding: FragmentSetBinding? = null
    private lateinit var tablaAdapter: SetAdapter
    private val tablaItemList = mutableListOf<TablaItem>()
    private val binding get() = _binding!!
    private var comboSelectedDate : Date = Date()
    private val roomSet = RoomSetDataSource();
    private val setViewModel: SetViewModel by viewModels {
        SetViewModelFactory(roomSet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            trainingPlan = it.getParcelable(ARG_TRAINING_PLAN, TrainingPlan::class.java)
            training = it.getParcelable(ARG_TRAINING, Training::class.java)
            exercise = it.getParcelable(ARG_EXERCISE, Exercise::class.java)
        }
    }

    private fun insertSetRoom(set: Set): Int {
        setViewModel.insertSet(comboSelectedDate, set, training!!, exercise!!)
        return 0;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetBinding.inflate(inflater, container, false)
        val root: View = binding.root
        tablaAdapter = SetAdapter(tablaItemList, ::insertSetRoom)
        binding.list.adapter = tablaAdapter

        getSet()
        getSetDays()
        addSet()
        showSet()
        buttonClickAddSet()
        addDaysToSpinner()

        return root
    }

    private fun addDaysToSpinner() {
        setViewModel.days.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    spinnerOfDays(it.data!!)
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun spinnerOfDays(data: List<Date>) {
        val spinner = binding.spinnerDayOfSet

        val dateStrings = data.map { date ->
            val cal = Calendar.getInstance()
            cal.time = date
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
            val year = cal.get(Calendar.YEAR)
            "$day\n$month\n$year"
        }.distinct()

        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, dateStrings)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDate = data[position]
                comboSelectedDate = selectedDate
                getSet()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun getSet() {
        setViewModel.getSet(comboSelectedDate, training!!.id, exercise!!.id)
    }

    private fun getSetDays() {
        setViewModel.getDaysOfSet(training!!.id, exercise!!.id)
    }

    private fun showSet() {
        setViewModel.item.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if(it.data!!.count() < 4){
                        tablaItemList.clear()
                        tablaItemList.addAll(it.data ?: emptyList())
                        for (i in 0 until 4 - it.data!!.count()) {
                            val nuevaFila = TablaItem(0, i + it.data!!.count() + 1, "", "", "", "")
                            tablaItemList.add(nuevaFila)
                        }
                        tablaAdapter.notifyDataSetChanged()
                    }else{
                        tablaItemList.clear()
                        tablaItemList.addAll(it.data ?: emptyList())
                        tablaAdapter.notifyDataSetChanged()
                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun addSet() {
        setViewModel.created.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    val id = it.data
                    Log.i("id", it.data.toString())
                    getSet()
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }
    }

    private fun buttonClickAddSet() {
        binding.imageButtonNew.setOnClickListener{
            agregarFila()
        }
    }

    private fun agregarFila() {
        val nuevaFila = TablaItem(0, 0, "", "", "", "")
        tablaItemList.add(nuevaFila)
        tablaAdapter.notifyItemInserted(tablaItemList.size)
    }

}