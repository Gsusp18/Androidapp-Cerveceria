package com.example.lahermandad.view.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.lahermandad.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class UsuarioFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttom = view.findViewById<BottomNavigationView>(R.id.buttonNavigationHome)
        buttom.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> findNavController().navigate(R.id.action_usuarioFragment_to_HomeFragment)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_usuario, container, false)
        val nombreCompleto= view.findViewById<EditText>(R.id.nombreCompleto)
        val correoCompleto= view.findViewById<EditText>(R.id.correoCompleto)
        val direccion = view.findViewById<EditText>(R.id.direccion)

        val btmeditnombre = view.findViewById<ImageButton>(R.id.nombreedit)
        val btmeditcorreo = view.findViewById<ImageButton>(R.id.nombreedit2)
        val btmeditdir = view.findViewById<ImageButton>(R.id.nombreedit3)

        nombreCompleto.isEnabled = false
        correoCompleto.isEnabled = false
        direccion.isEnabled = false

        btmeditnombre.setOnClickListener {
            if (nombreCompleto.isEnabled == false){
                nombreCompleto.isEnabled = true
            } else if (nombreCompleto.isEnabled == true) {
                nombreCompleto.isEnabled = false
            }
        }

        btmeditcorreo.setOnClickListener {
            if (correoCompleto.isEnabled == false){
                correoCompleto.isEnabled = true
            } else if (correoCompleto.isEnabled == true) {
                correoCompleto.isEnabled = false
            }
        }

        btmeditdir.setOnClickListener {
            if (direccion.isEnabled == false){
                direccion.isEnabled = true
            } else if (direccion.isEnabled == true) {
                direccion.isEnabled = false
            }
        }


        val btmcamara = view.findViewById<Button>(R.id.botoncamara)
        btmcamara.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 123)
        }

        val btmgaleria = view.findViewById<Button>(R.id.botongaleria)
        btmgaleria.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent, 456)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageView = view?.findViewById<ImageView>(R.id.foto_perfil)
        if (requestCode == 123 ){
            var bitmap=data?.extras?.get("data") as Bitmap
            imageView?.setImageBitmap(bitmap)
        } else if (requestCode == 456){
            imageView?.setImageURI(data?.data)
        }
    }

}