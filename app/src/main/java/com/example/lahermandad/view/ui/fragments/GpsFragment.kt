package com.example.lahermandad.view.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.lahermandad.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.type.LatLng


class GpsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap : GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_gps, container, false)
        val mapFragment=this.childFragmentManager.findFragmentById(R.id.VistaMapa) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttom = view.findViewById<BottomNavigationView>(R.id.buttonNavigationMenu)
        buttom.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> findNavController().navigate(R.id.action_gpsFragment_to_homeFragment)
                R.id.recomendado -> findNavController().navigate(R.id.action_gpsFragment_to_recomendadoFragment)
                R.id.tienda -> findNavController().navigate(R.id.action_gpsFragment_to_tiendaFragment)
            }
        }

    }

    override fun onMapReady(map: GoogleMap) {
        val bogota = com.google.android.gms.maps.model.LatLng (4.60971, -74.08175)
        map?.let {
            this.googleMap = it
            map.addMarker(MarkerOptions().position(bogota))
        }
        enableLocation()

    }

    private fun localizacionPermisos()=ContextCompat.checkSelfPermission(this.requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableLocation(){
        if(!::googleMap.isInitialized)return
        if(localizacionPermisos()){
            googleMap.isMyLocationEnabled = true
        }else{
            requestLocationPermision()
        }
    }
    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }
    private fun requestLocationPermision() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION
        )){
            Toast.makeText(context,"requiere activar permisos",Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION ->
                if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    googleMap.isMyLocationEnabled=true
                }else{
                    Toast.makeText(context,"Para activar localizacion, ir a ajustes y activar permisos",Toast.LENGTH_SHORT).show()
                }else ->{}
        }
    }

}