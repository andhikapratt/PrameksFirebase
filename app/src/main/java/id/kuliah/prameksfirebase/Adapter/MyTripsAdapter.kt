package id.kuliah.prameksfirebase.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import id.kuliah.prameksfirebase.ChildAttribute.DetBeliChild
import id.kuliah.prameksfirebase.MyTripsActivity
import id.kuliah.prameksfirebase.R
import id.kuliah.prameksfirebase.ReviewTiketActivity

class MyTripsAdapter (
    val mCtx: MyTripsActivity,
    val layoutResId: Int,
    val keretaList: List<DetBeliChild>): ArrayAdapter<DetBeliChild>(mCtx, layoutResId, keretaList){
    //==================================================================================================
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val namakrt = view.findViewById<TextView>(R.id.tv_nm_krt)
        val asall = view.findViewById<TextView>(R.id.tv_asal)
        val tujuann = view.findViewById<TextView>(R.id.tv_tujuan)
        val jambrkk = view.findViewById<TextView>(R.id.tv_jambrk)
        val jamsmp = view.findViewById<TextView>(R.id.tv_jamsmp)
        val hari = view.findViewById<TextView>(R.id.tv_hari)
        val kode = view.findViewById<TextView>(R.id.tv_kd_booking)
        val harga = view.findViewById<TextView>(R.id.tv_harga)

        val tiket = keretaList[position]

        namakrt.text = tiket.namakrt
        asall.text = tiket.asal
        tujuann.text = tiket.tujuan
        jambrkk.text = tiket.jambrk
        jamsmp.text = tiket.jamsmp
        hari.text = tiket.hari
        kode.text = tiket.kodebayar
        harga.text = tiket.harga

        return view
    }
}