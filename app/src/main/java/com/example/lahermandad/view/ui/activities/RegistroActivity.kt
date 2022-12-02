package com.example.lahermandad.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.example.lahermandad.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.Date

class RegistroActivity : AppCompatActivity() {
    lateinit var registrobutton:Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var name:EditText
    private lateinit var date:EditText

    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        firebaseAuth = Firebase.auth
        registrobutton = findViewById(R.id.registrobutton)
        val correo=findViewById<EditText>(R.id.correo)
        val contrasena=findViewById<EditText>(R.id.password)
        name=findViewById(R.id.nombreCompleto)
        date=findViewById(R.id.FechaN)

        val contraseñaconfirmar=findViewById<EditText>(R.id.confcont)

        database=FirebaseDatabase.getInstance()
        dbReference=database.reference.child("Usuario")


        registrobutton.setOnClickListener {
            var pass1 = contrasena.text.toString()
            var pass2 = contraseñaconfirmar.text.toString()
            if (pass1.equals(pass2)){
                crearUsuario(correo.text.toString(),contrasena.text.toString())
            }else{
                Toast.makeText(baseContext,"La contraseña no coincide", Toast.LENGTH_SHORT).show()
                contrasena.requestFocus()
            }
        }
    }
    private fun crearUsuario(correo:String, contrasena:String){
        val newname:String = name.text.toString()
        val newemail:String = correo
        firebaseAuth.createUserWithEmailAndPassword(correo,contrasena)
            .addOnCompleteListener(this) {
                Task->if(Task.isSuccessful){
                    val user=firebaseAuth.currentUser
                    val userdb=dbReference.child(user?.uid.toString())
                    userdb.child("Name").setValue(newname)
                    userdb.child("Email").setValue(newemail)
                    sendEmailVerification()
                    Toast.makeText(baseContext,"Usuario Creado Exitosamente, se requiere verificacion ",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,login::class.java))
                }else{
                    Toast.makeText(baseContext, "Error al crear Usuario",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun sendEmailVerification()
    {
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this) {
            task-> if(task.isSuccessful){
            }else{
            }
        }

    }
}