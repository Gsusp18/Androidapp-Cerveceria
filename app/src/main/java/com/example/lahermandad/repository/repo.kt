package com.example.lahermandad.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lahermandad.model.cervezas
import com.example.lahermandad.model.compras
import com.google.firebase.firestore.FirebaseFirestore

class repo {
    fun getBeerData():LiveData<MutableList<cervezas>> {
        val mutabledata = MutableLiveData<MutableList<cervezas>>()

        FirebaseFirestore.getInstance().collection("Cervezas").get()
            .addOnSuccessListener { result ->
                val listaData = mutableListOf<cervezas>()
                for (document in result){
                    val titulo = document.getString("titulo")
                    val precio = document.getString("precio")
                    val iva = document.getString("iva")
                    val subtotal = document.getString("subtotal")
                    val grado = document.getString("grado")
                    val image = document.getString("image")

                    val cerveza = cervezas(titulo!!,precio!!,image!!, iva!!, subtotal!!, grado!!)
                    listaData.add(cerveza)
                }
                mutabledata.value = listaData

        }
        return mutabledata

    }

    fun getComprasData():LiveData<MutableList<compras>>{

        val mutabledata=MutableLiveData<MutableList<compras>>()
        FirebaseFirestore.getInstance().collection("compras")
            .get().addOnSuccessListener {
                result ->
                val listData=mutableListOf<compras>()
                for(document in result){
                    val titulo=document.getString("titulo")
                    val precio=document.getString("precio")
                    val iva=document.getString("iva")
                    val subtotal=document.getString("subtotal")
                    val image=document.getString("image")
                    //val grado=document.getString("grado")
                    val compra=compras(titulo!!, precio!!, image!!, subtotal!!, iva!!)
                    listData.add(compra)

                }
                mutabledata.value=listData
            }
        return mutabledata
    }

}