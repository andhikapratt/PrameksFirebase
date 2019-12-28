package id.kuliah.prameksfirebase.Parcelable

import android.os.Parcel
import android.os.Parcelable

data class ParceAkun(val nama: String?, val ktp: String?, val email: String?, val notelp: String?) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(nama)
        writeString(ktp)
        writeString(email)
        writeString(notelp)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ParceAkun> = object : Parcelable.Creator<ParceAkun> {
            override fun createFromParcel(source: Parcel): ParceAkun = ParceAkun(source)
            override fun newArray(size: Int): Array<ParceAkun?> = arrayOfNulls(size)
        }
    }
}
