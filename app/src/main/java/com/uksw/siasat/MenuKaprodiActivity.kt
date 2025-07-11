package com.uksw.siasat

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.uksw.siasat.MatkulFragment // This is correct
// Make sure DosenFragment is in this package, otherwise adjust the import:
import com.uksw.siasat.DosenFragment // <--- Ensure DosenFragment is in 'com.uksw.siasat'
// If DosenFragment is in 'com.uksw.siasat.fragments', change this to:
// import com.uksw.siasat.fragments.DosenFragment

class MenuKaprodiActivity : AppCompatActivity() {

    // ---------- View references ----------
    private lateinit var totalMatkul : TextView
    private lateinit var totalDosen  : TextView
    private lateinit var dosenSudah  : TextView
    private lateinit var dosenBelum  : TextView
    private lateinit var btnCrudMatkul : Button
    private lateinit var btnCrudDosen  : Button

    // ---------- Firebase ----------
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_kaprodi)

        // --- Bind views ---
        totalMatkul   = findViewById(R.id.totalMatkul)
        totalDosen    = findViewById(R.id.totalDosen)
        dosenSudah    = findViewById(R.id.dosenSudah)
        dosenBelum    = findViewById(R.id.dosenBelum)
        btnCrudMatkul = findViewById(R.id.btnCrudMatkul)
        btnCrudDosen  = findViewById(R.id.btnCrudDosen)

        dbRef = FirebaseDatabase.getInstance().reference
        loadStatistik()
        if (savedInstanceState == null) showFragment(MatkulFragment())

        btnCrudMatkul.setOnClickListener {
            showFragment(MatkulFragment())
        }
        btnCrudDosen.setOnClickListener {
            showFragment(DosenFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun loadStatistik() {

        dbRef.child("matkul").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalMatkul.text = snapshot.childrenCount.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                totalMatkul.text = "0"
            }
        })

        dbRef.child("dosen").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var total = 0
                var denganTugas = 0

                snapshot.children.forEach { dosen ->
                    total++
                    if (dosen.child("tugas").exists()) {
                        denganTugas++
                    }
                }

                totalDosen.text = total.toString()
                dosenSudah.text = denganTugas.toString()
                dosenBelum.text = (total - denganTugas).toString()
            }
            override fun onCancelled(error: DatabaseError) {
                totalDosen.text = "0"
                dosenSudah.text = "0"
                dosenBelum.text = "0"
            }
        })
    }
}