package com.example.progressgym.ui.header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progressgym.R
import com.example.progressgym.databinding.FragmentHeaderBinding
import com.example.progressgym.databinding.FragmentInitialBinding

class HeaderFragment : Fragment() {

    private var _binding: FragmentHeaderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHeaderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //TODO Poner el username

        return root
    }
}