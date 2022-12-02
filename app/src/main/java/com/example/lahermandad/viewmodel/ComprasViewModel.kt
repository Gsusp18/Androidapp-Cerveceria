package com.example.lahermandad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lahermandad.model.compras
import com.example.lahermandad.repository.repo

class ComprasViewModel:ViewModel(){

    val repo = repo()
    fun fetchComprasData():LiveData<MutableList<compras>>{
        val mutableData=MutableLiveData<MutableList<compras>>()
        repo.getComprasData().observeForever {
            mutableData.value=it
        }
        return mutableData
    }
}