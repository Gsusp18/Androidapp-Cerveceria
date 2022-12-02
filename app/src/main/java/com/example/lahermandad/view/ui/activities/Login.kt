package com.example.lahermandad.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.lahermandad.R
import com.example.lahermandad.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class login : AppCompatActivity() {

    lateinit var registrobutton : Button
    lateinit var iniciobutton : Button
    lateinit var recuperarbutton : TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = Firebase.auth

        iniciobutton=findViewById(R.id.Binicio)
        registrobutton=findViewById(R.id.BRegistro)
        recuperarbutton=findViewById(R.id.BRecuperar)
        val correo=findViewById<EditText>(R.id.correolog)
        val contrasena=findViewById<EditText>(R.id.contrasenalog)

        iniciobutton.setOnClickListener {
            log(correo.text.toString(),contrasena.text.toString())
        }

        registrobutton.setOnClickListener {
            startActivity(Intent( this,RegistroActivity::class.java ))
        }

        recuperarbutton.setOnClickListener {
            startActivity(Intent( this,RecuperarActivity::class.java ))
        }
    }
    /*private fun log(correo:String, contrasena:String){
        firebaseAuth.signInWithEmailAndPassword(correo,contrasena)
            .addOnCompleteListener(this) {
                    task-> if(task.isSuccessful){
                val user = firebaseAuth.currentUser
                val verifica = user?.isEmailVerified
                if (verifica == true){
                Toast.makeText(baseContext,"Autenticación Exitosa", Toast.LENGTH_SHORT).show()
                startActivity(Intent( this, HomeActivity::class.java ))
                }else{
                    Toast.makeText(baseContext,"No ha verificado su correo",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(baseContext,"Error de Usuario o Contraseña", Toast.LENGTH_SHORT).show()
            }
        }

    }*/
    private fun log(correo:String, contrasena:String ){
        firebaseAuth.signInWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { Task ->
                if (Task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Toast.makeText(baseContext, user?.uid.toString(), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(baseContext, "usuario incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
    }
}