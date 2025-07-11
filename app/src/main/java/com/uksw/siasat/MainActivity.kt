package com.uksw.siasat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btnLoginKaprodi).setOnClickListener {
            startActivity(Intent(this, KaprodiLoginActivity::class.java))
        }

        findViewById<Button>(R.id.btnLoginDosen).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        findViewById<Button>(R.id.btnLoginMahasiswa).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        findViewById<Button>(R.id.btnLihatJadwal).setOnClickListener {
            startActivity(Intent(this, JadwalActivity::class.java))
        }
    }
}
