package id.kuliah.prameksfirebase
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.kuliah.prameksfirebase.Adapter.TiketAdapter
import id.kuliah.prameksfirebase.ChildAttribute.AkunChild
import id.kuliah.prameksfirebase.ChildAttribute.KeretaChild
import kotlinx.android.synthetic.main.activity_tampil_tiket.*

class TampilTiketActivity : AppCompatActivity() {
    lateinit var keretaList: MutableList<KeretaChild>

//==================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tampil_tiket)
        keretaList = mutableListOf()
        login()
    }
//==================================================================================================
    private fun login(){
        val bundle = intent.extras
        val asl = bundle?.get("asal").toString()
        val tuj = bundle?.get("tuju").toString()

        val ref = FirebaseDatabase.getInstance().reference
        val ref_user = ref.child("jadwal").orderByChild("asal").equalTo(asl)
        val ref_pass = ref.child("jadwal").orderByChild("tujuan").equalTo(tuj)

        ref_user.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p1: DataSnapshot){
                ref_pass.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot){
                        if(p1.exists() && p0.exists()){
                            keretaList.clear()
                            for(h in p0.children){
                                val kereta = h.getValue(KeretaChild::class.java)
                                keretaList.add(kereta!!)
                            }
                            val adapter = TiketAdapter(this@TampilTiketActivity, R.layout.keretalist, keretaList)
                            lvnya.adapter = adapter
                        }
                        else{
                            Toast.makeText(this@TampilTiketActivity, "Tiket Kosong!!", Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onCancelled(p0: DatabaseError){}
                })
            }
            override fun onCancelled(p1: DatabaseError){}
        })
    }
//==================================================================================================
}
