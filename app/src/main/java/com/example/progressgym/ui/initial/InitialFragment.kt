package com.example.progressgym.ui.initial

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progressgym.R
import com.example.progressgym.databinding.FragmentInitialBinding
import com.example.progressgym.ui.MainActivity
import com.example.progressgym.ui.trainingPlan.TrainingPlanFragment

class InitialFragment : Fragment() {

    private var _binding: FragmentInitialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInitialBinding.inflate(inflater, container, false)
        val root: View = binding.root

        changeFragment()

        return root
    }

    private fun changeFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }, 1000)
    }


}