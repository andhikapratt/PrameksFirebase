package id.kuliah.prameksfirebase.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import id.kuliah.prameksfirebase.*
import id.kuliah.prameksfirebase.ChildAttribute.KeretaChild

class TiketAdapter (
    val mCtx: TampilTiketActivity,
    val layoutResId: Int,
    val keretaList: List<KeretaChild>): ArrayAdapter<KeretaChild>(mCtx, layoutResId, keretaList) {
    lateinit var a: String
//==================================================================================================
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val namakrt = view.findViewById<TextView>(R.id.txt_nm_krt)
        val asall = view.findViewById<TextView>(R.id.txt_asal)
        val tujuann = view.findViewById<TextView>(R.id.txt_tujuan)
        val jambrkk = view.findViewById<TextView>(R.id.txt_jam_brk)
        val butt = view.findViewById<Button>(R.id.but_pil_ker)

        val tiket = keretaList[position]

        namakrt.text = tiket.namakrt
        asall.text = tiket.asal
        tujuann.text = tiket.tujuan
        jambrkk.text = tiket.jambrk

        butt.setOnClickListener {
            klik(tiket)
            val bundle = mCtx.intent.extras
            val id_penumpang = bundle?.get("ktp").toString()
            val hari = bundle?.get("hari").toString()

            mCtx.intent = Intent(mCtx, ReviewTiketActivity::class.java)
            mCtx.intent.putExtra("ktp",id_penumpang)
            mCtx.intent.putExtra("id_kereta",a)
            mCtx.intent.putExtra("hari",hari)
            mCtx.startActivity(mCtx.intent)
        }
        return  view
    }
//==================================================================================================
    fun klik(kereta: KeretaChild){
        a = kereta.idkrt.toString()
    }
//==================================================================================================
}