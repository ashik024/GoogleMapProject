package com.example.googlemapproject.LoginAndRegistrtion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.googlemapproject.MapsActivity
import com.example.googlemapproject.R
import com.example.googlemapproject.databinding.ActivityLoginBinding
import com.example.googlemapproject.databinding.ActivityRegisterBinding
import com.google.firebase.database.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var dataRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        Objects.requireNonNull(getSupportActionBar())!!.hide()

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        dataRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mapsproject-195dd-default-rtdb.firebaseio.com/")

        binding.regPage.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
        binding.submit.setOnClickListener{

            if (binding.edtTxtUname.text.isEmpty()){

                Toast.makeText(this,"Please Enter Your User Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            else if (binding.edtTxtUserPass.text.isEmpty()){

                Toast.makeText(this,"Please Enter Your Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            else if (!binding.check.isChecked){

                Toast.makeText(this,"Please Agree With T&c",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{

                dataRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.hasChild(binding.edtTxtUname.text.toString())){

                            var pass =snapshot.child(binding.edtTxtUname.text.toString())
                                .child("Password").getValue()

                            Log.e("pass",pass.toString())
                            Log.e("pass",binding.edtTxtUserPass.text.toString())

                            if (binding.edtTxtUserPass.text.toString().equals(pass.toString())){

                                Toast.makeText(this@LoginActivity,"Successfully LoggedIn", Toast.LENGTH_SHORT).show()

                                binding.edtTxtUname.text.clear()
                                binding.edtTxtUserPass.text.clear()

                                binding.check.setSelected(false);
                                startActivity(Intent(this@LoginActivity, MapsActivity::class.java))


                            }
                            else{
                                Toast.makeText(this@LoginActivity,"Wrong PassWord", Toast.LENGTH_SHORT).show()
                            }
                        }else{

                            Toast.makeText(this@LoginActivity,"User Not Found", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.edtTxtUname.text.clear()
        binding.edtTxtUserPass.text.clear()
        binding.check.setSelected(false);
    }
}