package id.kuliah.prameksfirebase.ChildAttribute

class AkunChild(val ktp: String?,
                val nama: String?,
                val user: String?,
                val pass: String?,
                val email: String?,
                val notelp: String?){
    constructor(): this("","","", "", "", "")
}