package id.kuliah.prameksfirebase

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import id.kuliah.prameksfirebase.ChildAttribute.AkunChild
import id.kuliah.prameksfirebase.ChildAttribute.DetBeliChild
import kotlinx.android.synthetic.main.activity_pembayaran.*

class PembayaranActivity : AppCompatActivity() {

    private val kode = (0..10000000).random().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

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

            builder.setTitle("Konfirmasi")
            builder.setMessage("Pilih 'Ya' untuk memesan tiket")
            builder.setPositiveButton("Ya"){dialog, which ->
                val ref = FirebaseDatabase.getInstance().getReference("det_pesan")
                val det_pesan = DetBeliChild(kode, id_penumpang, id_kereta, bank.toString(), hari, "8000")
                ref.child(kode).setValue(det_pesan).addOnCompleteListener{

                }
                intent = Intent(this, DetailPembayaranActivity::class.java)
                startActivity(intent)
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
}
