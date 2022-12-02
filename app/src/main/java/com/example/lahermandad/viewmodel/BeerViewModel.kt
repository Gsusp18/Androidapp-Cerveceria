package com.example.lahermandad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lahermandad.model.cervezas
import com.example.lahermandad.repository.repo

class BeerViewModel:ViewModel() {
    val repo = repo()

    fun BeerData():LiveData<MutableList<cervezas>>{
        val mutabledata = MutableLiveData<MutableList<cervezas>>()
        repo.getBeerData().observeForever{ result ->
            mutabledata.value=result
        }
        return mutabledata
    }
}