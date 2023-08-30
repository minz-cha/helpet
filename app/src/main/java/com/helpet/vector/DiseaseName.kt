package com.helpet.vector
import android.os.Parcel
import android.os.Parcelable

data class DiseaseName(val name: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DiseaseName> {
        override fun createFromParcel(parcel: Parcel): DiseaseName {
            return DiseaseName(parcel)
        }

        override fun newArray(size: Int): Array<DiseaseName?> {
            return arrayOfNulls(size)
        }
    }
}