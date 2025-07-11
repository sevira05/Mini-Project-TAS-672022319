package com.uksw.siasat

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrasiMatkulActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi_matkul)

        val matkul1 = findViewById<CheckBox>(R.id.matkul1)
        val matkul2 = findViewById<CheckBox>(R.id.matkul2)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val selected = mutableListOf<String>()
            if (matkul1.isChecked) selected.add(matkul1.text.toString())
            if (matkul2.isChecked) selected.add(matkul2.text.toString())

            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener
            val ref = FirebaseDatabase.getInstance().getReference("registrasi_matkul").child(uid)
            ref.removeValue()
            selected.forEachIndexed { index, s ->
                ref.child("matkul_${index + 1}").setValue(s)
            }
            ref.child("timestamp").setValue(System.currentTimeMillis())
            Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}