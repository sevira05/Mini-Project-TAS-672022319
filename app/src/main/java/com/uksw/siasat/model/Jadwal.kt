package com.uksw.siasat.model

data class Jadwal(
    val namaMatkul: String = "",
    val namaDosen: String = "",
    val hari: String = "",
    val jam: String = "",
    val mahasiswa: String = "",
    var id: String? = null
)
