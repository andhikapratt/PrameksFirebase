package id.kuliah.prameksfirebase.Parcelable

import android.os.Parcel
import android.os.Parcelable

data class ParceLogin(val ktp: String?) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(ktp)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ParceLogin> = object : Parcelable.Creator<ParceLogin> {
            override fun createFromParcel(source: Parcel): ParceLogin = ParceLogin(source)
            override fun newArray(size: Int): Array<ParceLogin?> = arrayOfNulls(size)
        }
    }
}