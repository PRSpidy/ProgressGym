package com.example.progressgym.ui.set

import SetAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.TablaItem
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.model.TrainingPlan
import com.example.progressgym.data.repository.local.RoomSetDataSource
import com.example.progressgym.databinding.FragmentCommunBinding


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunBinding.inflate(inflater, container, false)
        val root: View = binding.root
        tablaAdapter = SetAdapter(tablaItemList)
        binding.list.adapter = tablaAdapter
        agregarFila()
        agregarFila()
        return root
    }

    private fun agregarFila() {




        val nuevaFila = TablaItem(1, "", "", "", "")
        tablaItemList.add(nuevaFila)
        tablaAdapter.notifyItemInserted(tablaItemList.size - 1)
    }

}