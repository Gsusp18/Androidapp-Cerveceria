package com.example.lahermandad.view.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lahermandad.R
import com.example.lahermandad.model.compras
import com.example.lahermandad.view.adapter.ComprasAdapter
import com.example.lahermandad.view.adapter.OnCompraItemClickListener
import com.example.lahermandad.viewmodel.ComprasViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore


class ComprasFragment : Fragment(), OnCompraItemClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ComprasAdapter
    lateinit var precioT:TextView
    lateinit var precioSub:TextView
    lateinit var precioIva:TextView
    lateinit var compraT:TextView
    val database:FirebaseFirestore=FirebaseFirestore.getInstance()
    private val viewModel by lazy { ViewModelProvider(this).get(ComprasViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttom = view.findViewById<BottomNavigationView>(R.id.buttonNavigationHome)
        buttom.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> findNavController().navigate(R.id.action_comprasFragment_to_homeFragment)
            }
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_compras, container, false)
        recyclerView=view.findViewById(R.id.recyclerviewcompra)
        precioT=view.findViewById(R.id.preciototal)
        precioSub=view.findViewById(R.id.preciosubtotal)
        precioIva=view.findViewById(R.id.ivatotal)
        compraT=view.findViewById(R.id.realizar)
        adapter= ComprasAdapter(requireContext(), this)
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=adapter
        observeData()
        preciototal()
        preciosubtotal()
        precioiva()
        compraT.setOnClickListener{
            realizarcompra()
        }
        return view
    }

    private fun observeData() {
        viewModel.fetchComprasData().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun preciototal(){
        database.collection("compras")
            .get()
            .addOnSuccessListener {
                result ->
                val preciounitario= mutableListOf<String>()
                for(document in result) {
                    val precio = document["precio"].toString()
                    preciounitario.add(precio!!)
                }
                val preciototal=preciounitario.mapNotNull { it.toIntOrNull() }.sum()
                precioT.setText(Integer.toString(preciototal))
            }


    }
    private fun preciosubtotal(){
        database.collection("compras")
            .get()
            .addOnSuccessListener {
                    result ->
                val subtotalunitario= mutableListOf<String>()
                for(document in result) {
                    val subtotal = document["subtotal"].toString()
                    subtotalunitario.add(subtotal!!)
                }
                val preciosubtotal=subtotalunitario.mapNotNull { it.toIntOrNull() }.sum()
                precioSub.setText(Integer.toString(preciosubtotal))
            }


    }

    private fun precioiva(){
        database.collection("compras")
            .get()
            .addOnSuccessListener {
                    result ->
                val ivaunit= mutableListOf<String>()
                for(document in result) {
                    val iva = document["iva"].toString()
                    ivaunit.add(iva!!)
                }
                val precioiva=ivaunit.mapNotNull { it.toIntOrNull() }.sum()
                precioIva.setText(Integer.toString(precioiva))
            }


    }
    private fun realizarcompra(){

        val builder=AlertDialog.Builder(requireContext())
        builder.setTitle("CompraLaHermandad")
        builder.setMessage("¿Desea finalizar la compra?")
        builder.setPositiveButton("Aceptar"){
            dialog,which->
            findNavController().navigate(R.id.action_comprasFragment_to_homeFragment)

        }
        builder.setNegativeButton("Cancelar", null)
            .show()
    }



    override fun onItemclick(cerveza: compras, position: Int) {
        database.collection("compras")
            .document(cerveza.titulo)
            .delete()
    }


}