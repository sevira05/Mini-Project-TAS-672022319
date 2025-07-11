package com.uksw.siasat

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class InputNilaiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(32, 32, 32, 32)

        val mahasiswaIdField = EditText(this)
        mahasiswaIdField.hint = "ID Mahasiswa"
        layout.addView(mahasiswaIdField)

        val matkulField = EditText(this)
        matkulField.hint = "Mata Kuliah"
        layout.addView(matkulField)

        val nilaiField = EditText(this)
        nilaiField.hint = "Nilai"
        layout.addView(nilaiField)

        val btnSimpan = Button(this)
        btnSimpan.text = "Simpan Nilai"
        btnSimpan.setOnClickListener {
            val mhsId = mahasiswaIdField.text.toString().trim()
            val matkul = matkulField.text.toString().trim()
            val nilai = nilaiField.text.toString().trim()
            if (mhsId.isNotEmpty() && matkul.isNotEmpty() && nilai.isNotEmpty()) {
                val ref = FirebaseDatabase.getInstance().getReference("nilai").push()
                ref.setValue(mapOf(
                    "mahasiswaId" to mhsId,
                    "matkul" to matkul,
                    "nilai" to nilai
                ))
                Toast.makeText(this, "Nilai disimpan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
        }

        layout.addView(btnSimpan)
        setContentView(layout)
    }
}