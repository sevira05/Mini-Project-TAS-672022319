package com.uksw.siasat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uksw.siasat.MahasiswaFragment

class MenuMahasiswaActivity : AppCompatActivity() {

    private lateinit var nilaiView: TextView
    private lateinit var logoutBtn: Button
    private lateinit var btnCrudMahasiswa: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_mahasiswa)

        nilaiView = findViewById(R.id.nilaiView)
        logoutBtn = findViewById(R.id.logoutBtn)
        btnCrudMahasiswa = findViewById(R.id.btnCrudMahasiswa)

        loadNilaiMahasiswa()

        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnCrudMahasiswa.setOnClickListener {
            showFragment(MahasiswaFragment())
        }

        if (savedInstanceState == null) {
            showFragment(MahasiswaFragment())
        }
    }

    private fun loadNilaiMahasiswa() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            nilaiView.text = "Pengguna tidak terautentikasi."
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("nilai").child(uid)
        ref.get().addOnSuccessListener { snapshot ->
            val result = StringBuilder()
            if (snapshot.exists()) {
                for (nilai in snapshot.children) {
                    result.append("${nilai.key}: ${nilai.value}\n")
                }
                nilaiView.text = result.toString()
            } else {
                nilaiView.text = "Belum ada data nilai."
            }
        }.addOnFailureListener {
            nilaiView.text = "Gagal memuat data nilai."
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mahasiswaFragmentContainer, fragment)
            .commit()
    }
}
