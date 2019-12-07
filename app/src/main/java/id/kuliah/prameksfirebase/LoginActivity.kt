package id.kuliah.prameksfirebase

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import id.kuliah.prameksfirebase.ChildAttribute.AkunChild
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var et_user: EditText
    lateinit var et_pass: EditText
    lateinit var childList: MutableList<AkunChild>
//==================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "Login"

        et_user = findViewById(R.id.et_user)
        et_pass = findViewById(R.id.et_pass)
        childList = mutableListOf()

        bt_go.setOnClickListener{
            login()
        }

        txt_register.setOnClickListener{
            intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        show_pass.setOnClickListener {
            if(show_pass.text.toString().equals("show")){
                et_pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                show_pass.text = "hide"
            } else{
                et_pass.transformationMethod = PasswordTransformationMethod.getInstance()
                show_pass.text = "show"
            }
        }
    }
//==================================================================================================
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this@LoginActivity)
        builder.setTitle("Keluar")
        builder.setMessage("Apakah anda yakin?")
        builder.setPositiveButton("Ya"){dialog, which ->
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        builder.setNegativeButton("Tidak"){dialog,which ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
//==================================================================================================
    private fun login(){
        val user = et_user.text.toString().trim()
        val pass = et_pass.text.toString().trim()
        val loading = ProgressDialog(this@LoginActivity)

        if(user.isEmpty()){
            et_user.error = "Masukkan Username!!"
            return
        }

        if(pass.isEmpty()){
            et_pass.error = "Masukkan Password!!"
            return
        }

        val ref = FirebaseDatabase.getInstance().reference
        val ref_user = ref.child("akun").orderByChild("user").equalTo(user)
        val ref_pass = ref.child("akun").orderByChild("pass").equalTo(pass)

        //cek user
        ref_user.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p1: DataSnapshot){
                loading.setMessage("Memuat data...")
                loading.show()
                ref_pass.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot){
                        //jika user & pass ditemukan
                        if(p1.exists() && p0.exists()){
                            childList.clear()
                            for(h in p0.children){
                                val hero = h.getValue(AkunChild::class.java)
                                childList.add(hero!!)
                                val ktpp = hero.ktp

                                intent = Intent(this@LoginActivity, CariTiketActivity::class.java)
                                intent.putExtra("ktp",ktpp)
                                loading.dismiss()
                                startActivity(intent)
                            }
                        }
                        else{
                            Toast.makeText(this@LoginActivity, "Data Tidak Ditemukan!!", Toast.LENGTH_LONG).show()
                            loading.dismiss()
                        }
                    }
                    override fun onCancelled(p0: DatabaseError){}
                })
            }
            override fun onCancelled(p1: DatabaseError){}
        })

    loading.dismiss()
    }
//==================================================================================================
}
