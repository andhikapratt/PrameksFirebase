package id.kuliah.prameksfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.kuliah.prameksfirebase.ChildAttribute.KeretaChild
import id.kuliah.prameksfirebase.Parcelable.ParceTiket
import kotlinx.android.synthetic.main.activity_review_tiket.*
import kotlinx.android.synthetic.main.activity_review_tiket.txt_asal
import kotlinx.android.synthetic.main.activity_review_tiket.txt_tujuan
import kotlinx.android.synthetic.main.keretalist.*

class ReviewTiketActivity : AppCompatActivity() {

    lateinit var keretaList: MutableList<KeretaChild>
//==================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_tiket)
        supportActionBar?.title = "Detail Tiket"
        keretaList = mutableListOf()
        tampil()

        bt_lanjutt.setOnClickListener{
            val bundle = intent.extras
            val id_penumpang = bundle?.get("ktp").toString()
            val id_kereta = bundle?.get("id_kereta").toString()
            val hari = bundle?.get("hari").toString()

            intent = Intent(this, DataPenumpangActivity::class.java)
            intent.putExtra("ktp", id_penumpang)
            intent.putExtra("id_kereta", id_kereta)
            intent.putExtra("hari", hari)
            startActivity(intent)
        }
    }
//==================================================================================================
    private fun tampil(){
        val bundle = intent.extras
        val id_kereta = bundle?.get("id_kereta").toString()
        val hari = bundle?.get("hari").toString()
        val ref = FirebaseDatabase.getInstance().reference
        val ref_user = ref.child("jadwal").orderByChild("idkrt").equalTo(id_kereta)

        ref_user.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot){
                if(p0.exists()){
                    for(h in p0.children){
                        val kereta = h.getValue(KeretaChild::class.java)
                        keretaList.add(kereta!!)

                        txt_nmkrt.text = kereta.namakrt
                        txt_asal.text = kereta.asal
                        txt_tujuan.text = kereta.tujuan
                        txt_jb.text = kereta.jambrk
                        txt_js.text = kereta.jamsmp
                        txt_harga.text = kereta.harga.toString()
                        txt_hari.text = hari
                    }
                }
                else{
                    Toast.makeText(this@ReviewTiketActivity, "Tiket Kosong!!", Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(p0: DatabaseError){}
        })
    }
//==================================================================================================
}
