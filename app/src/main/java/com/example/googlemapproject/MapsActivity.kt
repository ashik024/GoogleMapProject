package com.example.googlemapproject

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.googlemapproject.ModelClass.LocationClass

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*
import android.location.Geocoder




class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    var currentLocation : Location? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    val REQUEST_CODE = 101

    var locationList = mutableListOf<LocationClass>()

//    var locationList: ArrayList<LocationClass> = arrayListOf<LocationClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
    Objects.requireNonNull(getSupportActionBar())!!.hide()

    window.statusBarColor = ContextCompat.getColor(this, R.color.black)

    locationList.add(LocationClass("Bangladesh Air Force Museum",23.7795278,90.3788181))
    locationList.add(LocationClass("Bangladesh Army Stadium",23.8054482,90.4019449))
    locationList.add(LocationClass("Bangladesh National Parliament",23.762466,90.3763924))
    locationList.add(LocationClass("Chandrima Udyan",23.7666866,90.3759511))
    locationList.add(LocationClass("Dhaka New Market",23.7331937,90.3815777))
    locationList.add(LocationClass("Dhakeshwari Mandir",23.7231774,90.3878913))
    locationList.add(LocationClass("Hatirjheel Park",23.7711643,90.4171034))
    locationList.add(LocationClass("Hazrat Shahjalal International Airport",23.8434344,90.4007365))
    locationList.add(LocationClass("Lalbagh Fort",23.7188605,90.3859709))
    locationList.add(LocationClass("Liberation War Museum",23.7757194,90.367496))
    locationList.add(LocationClass("Novo Theatre",23.7638254,90.3851293))
    locationList.add(LocationClass("Shaheed Minar",23.727238,90.3944363))





        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()

    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            return
        }

        val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null){
                currentLocation = location
                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)
                supportMapFragment!!.getMapAsync(this@MapsActivity)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
        val addresses: List<Address>
        var geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            currentLocation!!.latitude,
            currentLocation!!.longitude,
            1
        )


        val address: String = addresses[0].getAddressLine(0)
        val markerOptions = MarkerOptions().position(latLng).title(address.toString())
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
        googleMap.addMarker(markerOptions)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLocation()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}