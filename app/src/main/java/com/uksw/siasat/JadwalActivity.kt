package com.uksw.siasat

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.uksw.siasat.model.Jadwal
import android.util.TypedValue

class JadwalActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var jadwalLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal)

        dbRef = FirebaseDatabase.getInstance().reference
        jadwalLayout = findViewById(R.id.JadwalLayout)

        tampilkanJadwal()
    }

    private fun tampilkanJadwal() {
        dbRef.child("jadwal").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                jadwalLayout.removeAllViews()
                if (!snapshot.exists()) {
                    val noDataTextView = TextView(this@JadwalActivity).apply {
                        text = "Belum ada data jadwal."
                        textSize = 18f // Menggunakan float
                        setTextColor(resources.getColor(android.R.color.white, theme))
                        gravity = android.view.Gravity.CENTER_HORIZONTAL
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, 32, 0, 0)
                        }
                    }
                    jadwalLayout.addView(noDataTextView)
                    return
                }

                for (data in snapshot.children) {
                    val jadwal = data.getValue(Jadwal::class.java)
                    jadwal?.let {
                        val textView = TextView(this@JadwalActivity).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            ).apply {

                                val marginPx = TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics
                                ).toInt()
                                setMargins(0, 0, 0, marginPx)
                            }

                            text = "Mata Kuliah: ${it.namaMatkul}\n" +
                                    "Dosen: ${it.namaDosen}\n" +
                                    "Hari: ${it.hari}, Jam: ${it.jam}\n" +
                                    "Mahasiswa: ${it.mahasiswa}"
                            textSize = 16f
                            setTextColor(resources.getColor(android.R.color.white, theme))
                            setPadding(
                                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics).toInt(),
                                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics).toInt(),
                                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics).toInt(),
                                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics).toInt()
                            )
                            setBackgroundResource(R.drawable.rounded_card_background)
                        }
                        jadwalLayout.addView(textView)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@JadwalActivity, "Gagal memuat jadwal: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
