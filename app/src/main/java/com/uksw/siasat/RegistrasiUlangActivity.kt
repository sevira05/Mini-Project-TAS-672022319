package com.uksw.siasat

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrasiUlangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi_ulang)

        val namaField = findViewById<EditText>(R.id.namaField)
        val nimField = findViewById<EditText>(R.id.nimField)
        val semesterField = findViewById<EditText>(R.id.semesterField)
        val statusField = findViewById<EditText>(R.id.statusField)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener
            val ref = FirebaseDatabase.getInstance().getReference("registrasi_ulang").child(uid)
            ref.child("nama").setValue(namaField.text.toString())
            ref.child("nim").setValue(nimField.text.toString())
            ref.child("semester").setValue(semesterField.text.toString())
            ref.child("status").setValue(statusField.text.toString())
            ref.child("validasi").setValue("Belum divalidasi")

            Toast.makeText(this, "Registrasi ulang berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}
