package com.example.lahermandad.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lahermandad.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class RecomendadoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recomendados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttom = view.findViewById<BottomNavigationView>(R.id.buttonNavigationMenu)
        buttom.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> findNavController().navigate(R.id.action_recomendadoFragment_to_homeFragment)
                R.id.tienda -> findNavController().navigate(R.id.action_recomendadoFragment_to_tiendaFragment)
                R.id.mapa -> findNavController().navigate(R.id.action_recomendadoFragment_to_gpsFragment)
            }
        }

    }


}