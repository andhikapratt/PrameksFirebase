package id.kuliah.prameksfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.kuliah.prameksfirebase.ChildAttribute.AkunChild
import id.kuliah.prameksfirebase.ChildAttribute.KeretaChild
import kotlinx.android.synthetic.main.activity_data_penumpang.*
import kotlinx.android.synthetic.main.activity_review_tiket.*
import kotlinx.android.synthetic.main.activity_review_tiket.txt_harga

class DataPenumpangActivity : AppCompatActivity() {

    lateinit var akunn: MutableList<AkunChild>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_penumpang)
        supportActionBar?.title = "Data Penumpang"

        akunn = mutableListOf()
        val bundle = intent.extras
        val id_penumpang = bundle?.get("ktp").toString()
        val id_kereta = bundle?.get("id_kereta").toString()
        val hari = bundle?.get("hari").toString()

        tampil()

        bt_go.setOnClickListener {
            intent = Intent(this, PembayaranActivity::class.java)
            intent.putExtra("ktp", id_penumpang)
            intent.putExtra("id_kereta", id_kereta)
            intent.putExtra("hari", hari)
            startActivity(intent)
        }
    }

    private fun tampil(){
        val bundle = intent.extras
        val id_penumpang = bundle?.get("ktp").toString()
        val ref = FirebaseDatabase.getInstance().reference
        val ref_user = ref.child("akun").orderByChild("ktp").equalTo(id_penumpang)

        ref_user.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot){
                if(p0.exists()){
                    for(h in p0.children){
                        val akunnya = h.getValue(AkunChild::class.java)
                        akunn.add(akunnya!!)
                        et_ktp.setText(akunnya.ktp)
                        et_nama.setText(akunnya.nama)
                        et_email.setText(akunnya.email)
                        et_notelp.setText(akunnya.notelp)
                    }
                }
                else{
                    Toast.makeText(this@DataPenumpangActivity, "Tiket Kosong!!", Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(p0: DatabaseError){}
        })
    }
}
