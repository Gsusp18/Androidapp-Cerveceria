package com.example.lahermandad.view.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.lahermandad.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        firebaseAuth= Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.perfil->{
                findNavController().navigate(R.id.action_homeFragment_to_UsuarioFragment)
                true
            }
            R.id.ayuda->{
                findNavController().navigate(R.id.action_homeFragment_to_atencionFragment)
                true
            }
            R.id.historial->{
                findNavController().navigate(R.id.action_homeFragment_to_ComprasFragment)
                true
            }
            R.id.salir->{
                firebaseAuth.signOut()
                findNavController().navigate(R.id.action_homeFragment_to_login)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardNos = view.findViewById<ImageView>(R.id.cardtienda)

        cardNos.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_TiendaFragment)
        }
        val cardCom = view.findViewById<ImageView>(R.id.cardcomentarios)

        cardCom.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_ComentariosFragment)
        }

        val cardDes = view.findViewById<ImageView>(R.id.cardrecomendado)

        cardDes.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_RecomendadoFragment)
        }
        val cardGps = view.findViewById<ImageView>(R.id.cardubicacion)

        cardGps.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_GpsFragment)
        }
        val cardCompra=view.findViewById<ImageView>(R.id.compra)
        cardCompra.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_ComprasFragment)
        }

    }

}
