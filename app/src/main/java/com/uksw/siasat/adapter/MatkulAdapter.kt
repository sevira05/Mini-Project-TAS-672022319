package com.uksw.siasat.adapter   // <- paket adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uksw.siasat.R
import com.uksw.siasat.model.Matkul

class MatkulAdapter(
    private val onClick: (Matkul) -> Unit
) : ListAdapter<Matkul, MatkulAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Matkul>() {
            override fun areItemsTheSame(a: Matkul, b: Matkul) = a.id == b.id
            override fun areContentsTheSame(a: Matkul, b: Matkul) = a == b
        }
    }

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val txtKode: TextView = v.findViewById(R.id.itemKode)
        val txtNama: TextView = v.findViewById(R.id.itemNama)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH =
        VH(LayoutInflater.from(p.context).inflate(R.layout.item_matkul, p, false))

    override fun onBindViewHolder(h: VH, i: Int) {
        val m = getItem(i)
        h.txtKode.text = m.kode
        h.txtNama.text = m.nama
        h.itemView.setOnClickListener { onClick(m) }
    }
}
