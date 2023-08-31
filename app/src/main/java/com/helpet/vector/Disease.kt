import android.os.Parcel
import android.os.Parcelable

data class Disease(
    val name: String,
    val species: String,
    val symptoms: String,
    val causes: String,
    val treatments: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(species)
        parcel.writeString(symptoms)
        parcel.writeString(causes)
        parcel.writeString(treatments)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Disease> {
        override fun createFromParcel(parcel: Parcel): Disease {
            return Disease(parcel)
        }

        override fun newArray(size: Int): Array<Disease?> {
            return arrayOfNulls(size)
        }
    }
}
