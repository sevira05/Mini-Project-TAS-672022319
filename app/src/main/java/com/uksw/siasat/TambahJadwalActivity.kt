package com.uksw.siasat

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class TambahJadwalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scroll = ScrollView(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(24, 24, 24, 24)
        scroll.addView(layout)
        setContentView(scroll)

        val db = FirebaseDatabase.getInstance().getReference("jadwal_kuliah")
        db.get().addOnSuccessListener {
            for (data in it.children) {
                val matkul = data.child("matkul").value ?: "-"
                val hari = data.child("hari").value ?: "-"
                val jam = data.child("jam").value ?: "-"

                val txt = TextView(this)
                txt.text = """
                    Matkul : $matkul
                    Hari   : $hari
                    Jam    : $jam
                """.trimIndent()
                txt.setPadding(0, 0, 0, 32)
                layout.addView(txt)
            }
        }
    }
}
