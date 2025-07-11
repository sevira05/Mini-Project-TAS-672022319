package com.uksw.siasat

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.uksw.siasat.model.Dosen
import com.uksw.siasat.adapter.DosenAdapter

class DosenFragment : Fragment(R.layout.fragment_dosen) {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: DosenAdapter
    private lateinit var dbRef: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.rvDosen)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = DosenAdapter { dosen -> showEditDialog(dosen) }
        recycler.adapter = adapter

        view.findViewById<FloatingActionButton>(R.id.fabAddDosen).setOnClickListener {
            showEditDialog(null)
        }

        dbRef = FirebaseDatabase.getInstance().getReference("dosen")
        loadDosen()
    }

    private fun loadDosen() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Dosen>()
                snapshot.children.forEach { snap ->
                    val item = snap.getValue(Dosen::class.java)
                    item?.id = snap.key
                    item?.let(list::add)
                }
                adapter.submitList(list)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun showEditDialog(dosen: Dosen?) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_edit_dosen)

        val edtNidn = dialog.findViewById<EditText>(R.id.edtNidn)
        val edtNamaDosen = dialog.findViewById<EditText>(R.id.edtNamaDosen)
        val btnSave = dialog.findViewById<Button>(R.id.btnSaveDosen)
        val btnDelete = dialog.findViewById<Button>(R.id.btnDeleteDosen)

        dosen?.let {
            edtNidn.setText(it.nidn)
            edtNamaDosen.setText(it.nama)
            btnDelete.visibility = View.VISIBLE
        } ?: run { btnDelete.visibility = View.GONE }

        btnSave.setOnClickListener {
            val nidn = edtNidn.text.toString()
            val nama = edtNamaDosen.text.toString()
            if (nidn.isBlank() || nama.isBlank()) {
                return@setOnClickListener
            }

            val key = dosen?.id ?: dbRef.push().key!!
            dbRef.child(key).setValue(Dosen(nidn, nama, key))
            dialog.dismiss()
        }

        btnDelete.setOnClickListener {
            dosen?.id?.let { dbRef.child(it).removeValue() }
            dialog.dismiss()
        }

        dialog.show()
    }
}