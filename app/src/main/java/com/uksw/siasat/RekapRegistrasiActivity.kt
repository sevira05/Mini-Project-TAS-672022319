package com.uksw.siasat

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class RekapRegistrasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scrollView = ScrollView(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(24, 24, 24, 24)
        scrollView.addView(layout)
        setContentView(scrollView)

        val db = FirebaseDatabase.getInstance().getReference("registrasi_ulang")
        db.get().addOnSuccessListener {
            for (data in it.children) {
                val nama = data.child("nama").value ?: "-"
                val nim = data.child("nim").value ?: "-"
                val semester = data.child("semester").value ?: "-"
                val status = data.child("status").value ?: "-"
                val validasi = data.child("validasi").value ?: "-"

                val txt = TextView(this)
                txt.text = """
                    🧑 Nama     : $nama
                    🎓 NIM      : $nim
                    🗓️ Semester : $semester
                    🔖 Status   : $status
                    ✅ Validasi : $validasi
                """.trimIndent()
                txt.setTextColor(0xFFFFFFFF.toInt())
                txt.setPadding(16, 8, 16, 24)
                layout.addView(txt)
            }
        }
    }
}
