package id.kuliah.prameksfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.kuliah.prameksfirebase.Adapter.MyTripsAdapter
import id.kuliah.prameksfirebase.Adapter.TiketAdapter
import id.kuliah.prameksfirebase.ChildAttribute.DetBeliChild
import id.kuliah.prameksfirebase.ChildAttribute.KeretaChild
import kotlinx.android.synthetic.main.activity_tampil_tiket.*

class MyTripsActivity : AppCompatActivity() {

    lateinit var keretaList: MutableList<DetBeliChild>
//==================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trips)
        supportActionBar?.title = "MyTrips"
        keretaList = mutableListOf()

        tampil()
    }
//==================================================================================================
    override fun onBackPressed() {
        val bundle = intent.extras
        val id_penumpang = bundle?.get("ktp").toString()

        intent = Intent(this, CariTiketActivity::class.java)
        intent.putExtra("ktp", id_penumpang)
        startActivity(intent)
    }

//==================================================================================================
    private fun tampil(){
        val bundle = intent.extras
        val id_penumpang = bundle?.get("ktp").toString()

        val ref = FirebaseDatabase.getInstance().reference
        val ref_pass = ref.child("det_pesan").orderByChild("ktp").equalTo(id_penumpang)

        ref_pass.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot){
                if(p0.exists()){
                    keretaList.clear()
                    for(h in p0.children){
                        val kereta = h.getValue(DetBeliChild::class.java)
                        keretaList.add(kereta!!)
                    }
                    val adapter = MyTripsAdapter(this@MyTripsActivity, R.layout.lv_mytrips, keretaList)
                    lvnya.adapter = adapter
                }
                else{
                    Toast.makeText(this@MyTripsActivity, "Tiket Kosong!!", Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(p0: DatabaseError){}
        })
    }
//==================================================================================================
}
