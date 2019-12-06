package id.kuliah.prameksfirebase.ChildAttribute

class DetBeliChild (val kodebayar: String?,
                    val ktp_byr: String?,
                    val kd_krt: String?,
                    val metode: String?,
                    val hari: String?,
                    val harga: String?){
    constructor(): this("","","", "", "", "")
}