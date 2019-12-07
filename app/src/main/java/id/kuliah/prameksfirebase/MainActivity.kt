package id.kuliah.prameksfirebase

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import id.kuliah.prameksfirebase.ChildAttribute.AkunChild
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var et_ktp: EditText
    lateinit var et_nama: EditText
    lateinit var et_user: EditText
    lateinit var et_pass: EditText
    lateinit var et_email: EditText
    lateinit var et_notelp: EditText

    lateinit var childList: MutableList<AkunChild>
//==================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Registrasi"

        et_ktp = findViewById(R.id.et_ktp)
        et_nama = findViewById(R.id.et_nama)
        et_user = findViewById(R.id.et_username)
        et_pass = findViewById(R.id.et_password)
        et_email = findViewById(R.id.et_email)
        et_notelp = findViewById(R.id.et_notelp)

        childList = mutableListOf()

        bt_daftar.setOnClickListener{
            saveHero()
        }

        txt_login.setOnClickListener{
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
//==================================================================================================
    override fun onBackPressed() {

    }
//==================================================================================================
    private fun saveHero() {
        val ktp = et_ktp.text.toString().trim()
        val nama = et_nama.text.toString().trim()
        val user = et_user.text.toString().trim()
        val pass = et_pass.text.toString().trim()
        val email = et_email.text.toString().trim()
        val notelp = et_notelp.text.toString().trim()
        val ref = FirebaseDatabase.getInstance().getReference("akun")

        if(ktp.isEmpty()){
            et_ktp.error = "Masukkan Nama!!"
            return
        }
        if(nama.isEmpty()){
            et_nama.error = "Masukkan Nim!!"
            return
        }
        if(user.isEmpty()){
            et_user.error = "Masukkan User!!"
            return
        }
        if(email.isEmpty()){
            et_email.error = "Masukkan email!!"
            return
        }
        if(notelp.isEmpty()){
            et_notelp.error = "Masukkan no telpon!!"
            return
        }

        val akun = AkunChild(ktp, nama, user, pass, email, notelp)
        ref.child(ktp).setValue(akun).addOnCompleteListener{
            Toast.makeText(this@MainActivity, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show()
        }
    }
//==================================================================================================
}
