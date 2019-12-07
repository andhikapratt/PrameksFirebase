package id.kuliah.prameksfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cari_tiket.*

class CariTiketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_tiket)
        supportActionBar?.title = "Cari Tiket"
        val loading = ProgressDialog(this)
        loading.dismiss()

        val bundle = intent.extras
        val id_penumpang = bundle?.get("ktp").toString()

        sp_asal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                tv_asal.text = getResources().getStringArray(R.array.asal_dd)[i]
            }
            override fun onNothingSelected(adapterView: AdapterView<*>){}
        }

        sp_tuj.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                tv_tuju.text = getResources().getStringArray(R.array.tuju_dd)[i]
            }
            override fun onNothingSelected(adapterView: AdapterView<*>){}
        }

        sp_hari.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                tv_hari.text = getResources().getStringArray(R.array.hari)[i]
            }
            override fun onNothingSelected(adapterView: AdapterView<*>){}
        }

        bt_cari.setOnClickListener{
            intent = Intent(this, TampilTiketActivity::class.java)
            var a = tv_asal.text.toString()
            var b = tv_tuju.text.toString()
            var c = tv_hari.text.toString()
            intent.putExtra("asal",a)
            intent.putExtra("tuju",b)
            intent.putExtra("hari",c)
            intent.putExtra("ktp",id_penumpang)
            startActivity(intent)
        }

        bt_mytrips.setOnClickListener {
            intent = Intent(this, MyTripsActivity::class.java)
            intent.putExtra("ktp",id_penumpang)
            startActivity(intent)
        }

    }
}
