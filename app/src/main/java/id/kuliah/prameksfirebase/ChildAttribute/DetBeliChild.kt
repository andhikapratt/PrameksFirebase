package id.kuliah.prameksfirebase.ChildAttribute

class DetBeliChild (val ktp: String?,
                    val nama: String?,
                    val namakrt: String,
                    val idkrt: String?,
                    val asal: String?,
                    val tujuan: String?,
                    val jambrk: String?,
                    val jamsmp: String?,
                    val harga: String?,
                    val metode: String?,
                    val kodebayar: String?,
                    val hari: String?){
    constructor(): this("",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "")
}