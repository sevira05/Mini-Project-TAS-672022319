package com.uksw.siasat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uksw.siasat.R
import com.uksw.siasat.model.Dosen

class DosenAdapter(
    private val onClick: (Dosen) -> Unit
) : ListAdapter<Dosen, DosenAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Dosen>() {
            override fun areItemsTheSame(a: Dosen, b: Dosen) = a.id == b.id
            override fun areContentsTheSame(a: Dosen, b: Dosen) = a == b
        }
    }

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val txtNidn: TextView = v.findViewById(R.id.itemNidn)
        val txtNamaDosen: TextView = v.findViewById(R.id.itemNamaDosen)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH =
        VH(LayoutInflater.from(p.context).inflate(R.layout.item_dosen, p, false))

    override fun onBindViewHolder(h: VH, i: Int) {
        val d = getItem(i)
        h.txtNidn.text = d.nidn
        h.txtNamaDosen.text = d.nama
        h.itemView.setOnClickListener { onClick(d) }
    }
}