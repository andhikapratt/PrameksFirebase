package id.kuliah.prameksfirebase

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.LocalDataHandler
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.PaymentMethod
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.UserAddress
import com.midtrans.sdk.corekit.models.UserDetail
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import id.kuliah.prameksfirebase.ChildAttribute.*
import id.kuliah.prameksfirebase.Parcelable.ParceAkun
import kotlinx.android.synthetic.main.activity_pembayaran_midtrans.*
import java.util.ArrayList

class PembayaranMidtrans : AppCompatActivity(), TransactionFinishedCallback {
    lateinit var akunn: MutableList<AkunChild>
    lateinit var kereta: MutableList<KeretaChild>

    //akun
    lateinit var ktpp: String
    lateinit var namaa: String
    lateinit var emaill: String
    lateinit var notelpp: String

    //kereta
    lateinit var idkrtt: String
    lateinit var namakrtt: String
    lateinit var asall: String
    lateinit var tujuann: String
    lateinit var jambrktt: String
    lateinit var jamsmpp: String
    lateinit var hargaa: String

    //kodebayar
    lateinit var kode:String
//==================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran_midtrans)

        akunn = mutableListOf()
        kereta = mutableListOf()
        kode = (0..10000000).random().toString()

        gopayClick.setOnClickListener {
            if(tv_boolean.getText().toString() == "0"){
                initMid()
                transactionRequester()

                val bundle = intent.extras
                val id_penumpang = bundle?.get("ktp").toString()
                val id_kereta = bundle?.get("id_kereta").toString()
                val hari = bundle?.get("hari").toString()
                val bank = "Gopay"

                val ref = FirebaseDatabase.getInstance().reference
                val ref_user = ref.child("akun").orderByChild("ktp").equalTo(id_penumpang)
                val ref_ker = ref.child("jadwal").orderByChild("idkrt").equalTo(id_kereta)

                ref_user.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(p1: DataSnapshot){
                        ref_ker.addValueEventListener(object: ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot){
                                if(p1.exists() && p0.exists()){
                                    for(h in p1.children){
                                        val akunnya = h.getValue(AkunChild::class.java)
                                        akunn.add(akunnya!!)

                                        ktpp = akunnya.ktp.toString()
                                        namaa = akunnya.nama.toString()
                                        emaill = akunnya.email.toString()
                                        notelpp = akunnya.notelp.toString()
                                    }
                                    for(h in p0.children){
                                        val keretanya = h.getValue(KeretaChild::class.java)
                                        kereta.add(keretanya!!)

                                        namakrtt = keretanya.namakrt.toString()
                                        idkrtt = keretanya.idkrt.toString()
                                        asall = keretanya.asal.toString()
                                        tujuann = keretanya.tujuan.toString()
                                        jambrktt = keretanya.jambrk.toString()
                                        jamsmpp = keretanya.jamsmp.toString()
                                        hargaa = keretanya.harga.toString()
                                    }

                                    val ref = FirebaseDatabase.getInstance().getReference("det_pesan")
                                    val det_pesan = DetBeliChild(ktpp, namaa, namakrtt,idkrtt, asall, tujuann, jambrktt, jamsmpp, hargaa, bank.toString(), kode, hari)

                                    ref.child(kode).setValue(det_pesan).addOnCompleteListener{
                                        //isi aksi
                                    }
                                }
                            }
                            override fun onCancelled(p0: DatabaseError){}
                        })
                    }
                    override fun onCancelled(p1: DatabaseError){}
                })
                MidtransSDK.getInstance().startPaymentUiFlow(this, PaymentMethod.GO_PAY)
            }
            else{
                Toast.makeText(this,"Anda Sudah Melakukan Pembayaran!!",Toast.LENGTH_SHORT).show()
            }
            tv_boolean.setText("1")
        }

        bt_selesaii.setOnClickListener {
            if(tv_boolean.getText().toString() == "0"){
                val builder = AlertDialog.Builder(this@PembayaranMidtrans)
                builder.setTitle("Batal")
                builder.setMessage("Apakah Ingin Membatalkan Pembayaran?")
                builder.setPositiveButton("Ya"){dialog, which ->
                    val bundle = intent.extras
                    val id_penumpang = bundle?.get("ktp").toString()
                    intent = Intent(this, CariTiketActivity::class.java)
                    intent.putExtra("ktp",id_penumpang)
                    startActivity(intent)
                }
                builder.setNegativeButton("Tidak"){dialog,which -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            else{
                val bundle = intent.extras
                val id_penumpang = bundle?.get("ktp").toString()
                intent = Intent(this, DetailPembayaranActivity::class.java)
                intent.putExtra("ktp",id_penumpang)
                intent.putExtra("kode",kode)
                startActivity(intent)
            }
        }
    }
//==================================================================================================

    override fun onBackPressed() {
        Toast.makeText(this, "Anda Tidak Bisa Kembali!!", Toast.LENGTH_SHORT).show()
    }

//==================================================================================================
    private fun initMid() {
        SdkUIFlowBuilder.init()
            .setClientKey(Constant.CLIENT_KEY)
            .setContext(this)
            .setTransactionFinishedCallback { result ->
                Log.w(
                    TAG,
                    result.response.statusMessage
                )
            }
            .setMerchantBaseUrl(Constant.BASE_URL)
            .enableLog(true)
            .setColorTheme(
                CustomColorTheme(
                    "#FFE51255",
                    "#B61548",
                    "#FFE51255"
                )
            )
            .buildSDK()
    }
//==================================================================================================
    override fun onTransactionFinished(transactionResult: TransactionResult) {
        Log.w(TAG, transactionResult.response.statusMessage)

    }
//==================================================================================================
    private fun transactionRequester() {
        val userDetail: UserDetail?
        userDetail = UserDetail()

        val ambil = intent.getParcelableExtra<ParceAkun>("dataa")
        val(nama, ktp, email, notelp) = ambil

        userDetail.userFullName = "$nama"
        userDetail.email = "$email"
        userDetail.phoneNumber = "$notelp"
        userDetail.userId = "$ktp"

        val userAddresses = ArrayList<UserAddress>()
        val userAddress = UserAddress()

        userAddress.address = "Kosongg"
        userAddress.city = "Kosongg"
        userAddress.addressType = com.midtrans.sdk.corekit.core.Constants.ADDRESS_TYPE_BOTH
        userAddress.zipcode = "12345"
        userAddress.country = "IDN"
        userAddresses.add(userAddress)
        userDetail.userAddresses = userAddresses
        LocalDataHandler.saveObject("user_details", userDetail)

        val transactionRequest = TransactionRequest(System.currentTimeMillis().toString() + "", 1.0)
        val itemDetails2 = ItemDetails("123", 1.0, 1, "Prameks")


        val itemDetailsList = ArrayList<ItemDetails>()
        itemDetailsList.add(itemDetails2)

        transactionRequest.itemDetails = itemDetailsList
        MidtransSDK.getInstance().transactionRequest = transactionRequest
    }
//==================================================================================================


//==================================================================================================
    companion object {
        private val TAG = "transactionresult"
    }
//==================================================================================================
}