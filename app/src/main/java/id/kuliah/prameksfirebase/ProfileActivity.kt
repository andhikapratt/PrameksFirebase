package id.kuliah.prameksfirebase

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.kuliah.prameksfirebase.ChildAttribute.AkunChild
import kotlinx.android.synthetic.main.activity_data_penumpang.*
import kotlinx.android.synthetic.main.activity_data_penumpang.et_email
import kotlinx.android.synthetic.main.activity_data_penumpang.et_ktp
import kotlinx.android.synthetic.main.activity_data_penumpang.et_nama
import kotlinx.android.synthetic.main.activity_data_penumpang.et_notelp
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    lateinit var akunn: MutableList<AkunChild>
//==================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.title = "Profile"
        akunn = mutableListOf()

        update()

        bt_update.setOnClickListener {
            val builder = AlertDialog.Builder(this@ProfileActivity)

            val ktpp = et_ktpp.getText().toString()
            val namaa = et_namaa.getText().toString()
            val emaill = et_emaill.getText().toString()
            val notelpp = et_notelpp.getText().toString()
            val passs = et_passwordd.getText().toString()
            val userr = et_usernamee.getText().toString()
            val ref = FirebaseDatabase.getInstance().getReference("akun")

            builder.setTitle("Update")
            builder.setMessage("Yakin Update Data?")
            builder.setPositiveButton("Ya"){dialog, which ->
                val akun = AkunChild(ktpp, namaa, userr, passs, emaill, notelpp)
                ref.child(ktpp).setValue(akun).addOnCompleteListener{
                    Toast.makeText(this@ProfileActivity, "Berhasil Mengubah Data", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Tidak"){dialog,which ->

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
//==================================================================================================
    private fun update(){
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
                        et_ktpp.setText(akunnya.ktp)
                        et_namaa.setText(akunnya.nama)
                        et_emaill.setText(akunnya.email)
                        et_notelpp.setText(akunnya.notelp)
                        et_passwordd.setText(akunnya.pass)
                        et_usernamee.setText(akunnya.user)
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError){}
        })
    }
//==================================================================================================
}
