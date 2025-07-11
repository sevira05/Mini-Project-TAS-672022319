package com.uksw.siasat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uksw.siasat.R
import com.uksw.siasat.model.Mahasiswa

class MahasiswaAdapter(
    private val onClick: (Mahasiswa) -> Unit
) : ListAdapter<Mahasiswa, MahasiswaAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Mahasiswa>() {
            override fun areItemsTheSame(a: Mahasiswa, b: Mahasiswa) = a.id == b.id
            override fun areContentsTheSame(a: Mahasiswa, b: Mahasiswa) = a == b
        }
    }

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val txtNim: TextView = v.findViewById(R.id.itemNim)
        val txtNamaMahasiswa: TextView = v.findViewById(R.id.itemNamaMahasiswa)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH =
        VH(LayoutInflater.from(p.context).inflate(R.layout.item_mahasiswa, p, false))

    override fun onBindViewHolder(h: VH, i: Int) {
        val mahasiswa = getItem(i)
        h.txtNim.text = "NIM: ${mahasiswa.nim}"
        h.txtNamaMahasiswa.text = mahasiswa.nama
        h.itemView.setOnClickListener { onClick(mahasiswa) }
    }
}
