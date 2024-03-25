package com.example.progressgym.ui.set

import SetAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.Set
import com.example.progressgym.data.model.TablaItem
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.RoomSetDataSource
import com.example.progressgym.databinding.FragmentCommunBinding
import com.example.progressgym.utils.Resource
import java.util.Date


private const val ARG_TRAINING_PLAN = "trainingPlan"
private const val ARG_TRAINING = "training"
private const val ARG_EXERCISE = "exercise"
class SetFragment : Fragment() {

    private var trainingPlan: TrainingPlan? = null
    private var training: Training? = null
    private var exercise: Exercise? = null
    private var _binding: FragmentCommunBinding? = null
    private lateinit var tablaAdapter: SetAdapter
    private val tablaItemList = mutableListOf<TablaItem>()
    private val binding get() = _binding!!
    private var editCreateSetPosition: Int = 0

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

    private fun insertSetRoom(set: Set, position: Int): Int {
        editCreateSetPosition = position
        setViewModel.insertSet(set, training!!, exercise!!)
        return 0;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunBinding.inflate(inflater, container, false)
        val root: View = binding.root
        tablaAdapter = SetAdapter(tablaItemList, ::insertSetRoom)
        binding.list.adapter = tablaAdapter

        getSet(Date())

        addSet()
        showSet()
        buttonClickAddSet()

        return root
    }

    private fun getSet(date: Date) {
        setViewModel.getSet(date, training!!.id, exercise!!.id)
    }

    private fun showSet() {
        setViewModel.item.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if(it.data!!.isEmpty()){
                        tablaItemList.clear()
                        for (i in 0 until 4) {
                            val nuevaFila = TablaItem(0, i + 1, "", "", "", "")
                            tablaItemList.add(nuevaFila)
                            tablaAdapter.notifyItemInserted(tablaItemList.size - 1)
                        }
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
                    tablaAdapter.updateItemId(id!!, editCreateSetPosition)
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

        tablaAdapter.notifyItemInserted(tablaItemList.size - 1)
    }

}