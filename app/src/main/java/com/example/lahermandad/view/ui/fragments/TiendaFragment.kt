package com.example.lahermandad.view.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lahermandad.R
import com.example.lahermandad.model.cervezas
import com.example.lahermandad.view.adapter.NosotrosAdapter
import com.example.lahermandad.view.adapter.OnBookItemClickListener
import com.example.lahermandad.viewmodel.BeerViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class TiendaFragment : Fragment(), OnBookItemClickListener {

    lateinit var adapter: NosotrosAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var firebaseAuth: FirebaseAuth
    val database:FirebaseFirestore=FirebaseFirestore.getInstance()
    private val viewModel by lazy { ViewModelProvider(this).get(BeerViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    lateinit var recyclerNos: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tienda, container, false)
        recyclerNos = view.findViewById(R.id.recyclerview)
        adapter = NosotrosAdapter(requireContext(), this)
        recyclerNos.layoutManager = LinearLayoutManager(context)
        recyclerNos.adapter = adapter

        observeData()

        return view
    }
    fun observeData(){
        viewModel.BeerData().observe(viewLifecycleOwner,Observer{
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.carrito->{
                findNavController().navigate(R.id.action_tiendaFragment_to_carritoFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.carrito_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttom = view.findViewById<BottomNavigationView>(R.id.buttonNavigationMenu)
        buttom.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> findNavController().navigate(R.id.action_tiendaFragment_to_HomeFragment)
                R.id.recomendado -> findNavController().navigate(R.id.action_tiendaFragment_to_RecomendadoFragment)
                R.id.mapa -> findNavController().navigate(R.id.action_tiendaFragment_to_GpsFragment)
            }
        }

    }

    override fun onItemclick(cerveza: cervezas, position: Int) {
       val titulo:String=cerveza.titulo
        val precio:String=cerveza.precio
        val image:String=cerveza.image
        val iva:String=cerveza.iva
        val subtotal:String=cerveza.subtotal
        val dato= hashMapOf(
            "titulo" to titulo,
            "precio" to precio,
            "iva" to iva,
            "subtotal" to subtotal,
            "image" to image


        )
        database.collection("compras")
            .document(titulo)
            .set(dato)
            .addOnSuccessListener {
                Toast.makeText(context, "El ítem fue añadido al carrito", Toast.LENGTH_SHORT).show()
            }
    }

}