package id.kuliah.prameksfirebase.ChildAttribute

class KeretaChild (val namakrt: String?,
                   val asal: String?,
                   val tujuan: String?,
                   val jambrk: String?,
                   val jamsmp: String?,
                   val idkrt: String?,
                   val harga: Int?){
    constructor(): this("","","", "","","", harga = 8000){}
}