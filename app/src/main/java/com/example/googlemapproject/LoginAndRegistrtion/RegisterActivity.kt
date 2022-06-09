package com.example.googlemapproject.LoginAndRegistrtion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.googlemapproject.MapsActivity
import com.example.googlemapproject.R
import com.example.googlemapproject.databinding.ActivityRegisterBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var dataRef: DatabaseReference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Objects.requireNonNull(getSupportActionBar())!!.hide()

        binding = DataBindingUtil.setContentView(this,R.layout.activity_register)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        dataRef=FirebaseDatabase.getInstance().getReferenceFromUrl("https://mapsproject-195dd-default-rtdb.firebaseio.com/")


        binding.emailCode.setOnClickListener{

            Toast.makeText(this,"Functionality Not Added",Toast.LENGTH_SHORT).show()
        }
        binding.submit.setOnClickListener{

            if (binding.edtTxtFName.text.isEmpty()){

                Toast.makeText(this,"Please Enter Your First Name",Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            else if (binding.edtTxtLName.text.isEmpty()){

                Toast.makeText(this,"Please Enter Your Last Name",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (binding.edtTxtEmail.text.isEmpty()){

                Toast.makeText(this,"Please Enter Your Email ",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//            else if (binding.edtTxtCode.text.isEmpty()){
//
//                Toast.makeText(this,"Please Enter Your Verification code",Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
            else if (binding.edtTxtUserName.text.isEmpty()){

                Toast.makeText(this,"Please Enter Your User Name",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (binding.edtTxtUserPass.text.isEmpty()){

                Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (binding.edtTxtUserPass.text.length<6){

                Toast.makeText(this,"Password Should Be AtLeast 6 Characters ",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (binding.edtTxtRetypePass.text.isEmpty()){

                Toast.makeText(this,"Please Retype Your Password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (!binding.edtTxtUserPass.text.toString().equals(binding.edtTxtRetypePass.text.toString())){

                Toast.makeText(this,"Password Doesn't Matches",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            else if (!binding.check.isChecked){

                Toast.makeText(this,"Please Agree With T&c",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{

            try {
                dataRef.child("users").child(binding.edtTxtUserName.text.toString())
                    .child("FName").setValue(binding.edtTxtFName.text.toString())

                dataRef.child("users").child(binding.edtTxtUserName.text.toString())
                    .child("LName").setValue(binding.edtTxtLName.text.toString())

                dataRef.child("users").child(binding.edtTxtUserName.text.toString())
                    .child("Email").setValue(binding.edtTxtEmail.text.toString())

                dataRef.child("users").child(binding.edtTxtUserName.text.toString())
                    .child("UserName").setValue(binding.edtTxtUserName.text.toString())

                dataRef.child("users").child(binding.edtTxtUserName.text.toString())
                    .child("Password").setValue(binding.edtTxtUserPass.text.toString())

                Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, LoginActivity::class.java))


            }catch (e: Exception){

                Toast.makeText(this,"Error: "+e.toString(),Toast.LENGTH_SHORT).show()
            }



            }
        }

    }


}