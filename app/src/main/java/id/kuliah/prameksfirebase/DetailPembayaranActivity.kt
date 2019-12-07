package id.kuliah.prameksfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail_pembayaran.*

class DetailPembayaranActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pembayaran)
        supportActionBar?.title = "Selesai"

        val bundle = intent.extras
        val id_penumpang = bundle?.get("ktp").toString()
        val kodenya = bundle?.get("kode").toString()

        textView6.text = kodenya

        bt_kembali.setOnClickListener {
            intent = Intent(this, CariTiketActivity::class.java)
            intent.putExtra("ktp", id_penumpang)
            startActivity(intent)
            finish()
        }
    }
    override fun onBackPressed() {

    }
}
