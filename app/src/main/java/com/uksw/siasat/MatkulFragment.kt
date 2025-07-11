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
import com.uksw.siasat.adapter.MatkulAdapter
import com.uksw.siasat.model.Matkul

class MatkulFragment : Fragment(R.layout.fragment_matkul) {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: MatkulAdapter
    private lateinit var dbRef: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.rvMatkul)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = MatkulAdapter { matkul -> showEditDialog(matkul) }
        recycler.adapter = adapter

        view.findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            showEditDialog(null)       // null = tambah baru
        }

        dbRef = FirebaseDatabase.getInstance().getReference("matkul")
        loadMatkul()
    }

    private fun loadMatkul() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Matkul>()
                snapshot.children.forEach { snap ->
                    val item = snap.getValue(Matkul::class.java)
                    item?.id = snap.key
                    item?.let(list::add)
                }
                adapter.submitList(list)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun showEditDialog(matkul: Matkul?) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_edit_matkul)

        val edtKode = dialog.findViewById<EditText>(R.id.edtKode)
        val edtNama = dialog.findViewById<EditText>(R.id.edtNama)
        val btnSave = dialog.findViewById<Button>(R.id.btnSave)
        val btnDelete = dialog.findViewById<Button>(R.id.btnDelete)

        matkul?.let {
            edtKode.setText(it.kode)
            edtNama.setText(it.nama)
            btnDelete.visibility = View.VISIBLE
        } ?: run { btnDelete.visibility = View.GONE }

        btnSave.setOnClickListener {
            val kode = edtKode.text.toString()
            val nama = edtNama.text.toString()
            if (kode.isBlank() || nama.isBlank()) return@setOnClickListener

            val key = matkul?.id ?: dbRef.push().key!!
            dbRef.child(key).setValue(Matkul(kode, nama, key))
            dialog.dismiss()
        }

        btnDelete.setOnClickListener {
            matkul?.id?.let { dbRef.child(it).removeValue() }
            dialog.dismiss()
        }

        dialog.show()
    }
}