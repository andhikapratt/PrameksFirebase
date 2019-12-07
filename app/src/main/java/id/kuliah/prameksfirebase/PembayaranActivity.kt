package id.kuliah.prameksfirebase

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.kuliah.prameksfirebase.ChildAttribute.AkunChild
import id.kuliah.prameksfirebase.ChildAttribute.DetBeliChild
import id.kuliah.prameksfirebase.ChildAttribute.KeretaChild
import kotlinx.android.synthetic.main.activity_pembayaran.*

class PembayaranActivity : AppCompatActivity() {

    lateinit var akunn: MutableList<AkunChild>
    lateinit var kereta: MutableList<KeretaChild>

    //akun
    lateinit var ktpp: String
    lateinit var namaa: String

    //kereta
    lateinit var idkrtt: String
    lateinit var namakrtt: String
    lateinit var asall: String
    lateinit var tujuann: String
    lateinit var jambrktt: String
    lateinit var jamsmpp: String
    lateinit var hargaa: String

    //kodebayar
    private val kode = (0..10000000).random().toString()
//==================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)
        supportActionBar?.title = "Pembayaran"

        akunn = mutableListOf()
        kereta = mutableListOf()

        sp_bank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                tv_bankk.text = getResources().getStringArray(R.array.bayar)[i]
            }
            override fun onNothingSelected(adapterView: AdapterView<*>){}
        }

        val bundle = intent.extras
        val id_penumpang = bundle?.get("ktp").toString()
        val id_kereta = bundle?.get("id_kereta").toString()
        val hari = bundle?.get("hari").toString()

        bt_selesai.setOnClickListener {
            val bank = tv_bankk.getText()
            val builder = AlertDialog.Builder(this@PembayaranActivity)

            val ref = FirebaseDatabase.getInstance().reference
            val ref_user = ref.child("akun").orderByChild("ktp").equalTo(id_penumpang)
            val ref_ker = ref.child("jadwal").orderByChild("idkrt").equalTo(id_kereta)

            builder.setTitle("Konfirmasi")
            builder.setMessage("Pilih 'Ya' untuk memesan tiket")
            builder.setPositiveButton("Ya"){dialog, which ->
                ref_user.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(p1: DataSnapshot){
                        ref_ker.addValueEventListener(object: ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot){
                                if(p1.exists() && p0.exists()){
                                    for(h in p1.children){
                                        val akunnya = h.getValue(AkunChild::class.java)
                                        akunn.add(akunnya!!)

                                        ktpp = akunnya.ktp.toString()
                                        namaa = akunnya.nama.toString()
                                    }
                                    for(h in p0.children){
                                        val keretanya = h.getValue(KeretaChild::class.java)
                                        kereta.add(keretanya!!)

                                        namakrtt = keretanya.namakrt.toString()
                                        idkrtt = keretanya.idkrt.toString()
                                        asall = keretanya.asal.toString()
                                        tujuann = keretanya.tujuan.toString()
                                        jambrktt = keretanya.jambrk.toString()
                                        jamsmpp = keretanya.jamsmp.toString()
                                        hargaa = keretanya.harga.toString()
                                    }

                                    val ref = FirebaseDatabase.getInstance().getReference("det_pesan")
                                    val det_pesan = DetBeliChild(ktpp, namaa, namakrtt,idkrtt, asall, tujuann, jambrktt, jamsmpp, hargaa, bank.toString(), kode, hari)
                                    ref.child(kode).setValue(det_pesan).addOnCompleteListener{

                                    }
                                    intent = Intent(this@PembayaranActivity, DetailPembayaranActivity::class.java)
                                    intent.putExtra("ktp", id_penumpang)
                                    intent.putExtra("kode", kode)
                                    startActivity(intent)
                                }
                            }
                            override fun onCancelled(p0: DatabaseError){}
                        })
                    }
                    override fun onCancelled(p1: DatabaseError){}
                })
            }

            builder.setNegativeButton("Tidak"){dialog,which ->
                intent = Intent(this, CariTiketActivity::class.java)
                intent.putExtra("ktp", id_penumpang)
                startActivity(intent)
            }

            builder.setNeutralButton("Batal"){_,_ ->

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
//==================================================================================================
}