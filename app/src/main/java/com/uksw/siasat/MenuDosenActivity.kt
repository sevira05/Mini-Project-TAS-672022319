package com.uksw.siasat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.uksw.siasat.DosenFragment

class MenuDosenActivity : AppCompatActivity() {

    private lateinit var btnInputNilai: Button
    private lateinit var btnLogout: Button
    private lateinit var btnCrudDosen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_dosen)

        btnInputNilai = findViewById(R.id.btnInputNilai)
        btnLogout = findViewById(R.id.btnLogout)
        btnCrudDosen = findViewById(R.id.btnCrudDosen)



        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnCrudDosen.setOnClickListener {
            showFragment(DosenFragment())
        }

        if (savedInstanceState == null) {
            showFragment(DosenFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.dosenFragmentContainer, fragment)
            .commit()
    }
}