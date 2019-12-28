package id.kuliah.prameksfirebase.Parcelable

import android.os.Parcel
import android.os.Parcelable

data class ParceTiket(val idkrt: String?, val nmkrt: String?) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(idkrt)
        writeString(nmkrt)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ParceTiket> = object : Parcelable.Creator<ParceTiket> {
            override fun createFromParcel(source: Parcel): ParceTiket = ParceTiket(source)
            override fun newArray(size: Int): Array<ParceTiket?> = arrayOfNulls(size)
        }
    }
}