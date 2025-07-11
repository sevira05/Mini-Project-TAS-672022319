package com.uksw.siasat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class ValidasiRegistrasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validasi_registrasi)

        val nimField = findViewById<EditText>(R.id.nimField)
        val validBtn = findViewById<Button>(R.id.validBtn)

        validBtn.setOnClickListener {
            val nim = nimField.text.toString().trim()
            if (nim.isEmpty()) {
                Toast.makeText(this, "NIM tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ref = FirebaseDatabase.getInstance().getReference("registrasi_ulang")
            ref.orderByChild("nim").equalTo(nim)
                .get().addOnSuccessListener {
                    if (it.children.count() == 0) {
                        Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                    } else {
                        for (data in it.children) {
                            data.ref.child("validasi").setValue("Tervalidasi")
                        }
                        Toast.makeText(this, "Validasi berhasil", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}