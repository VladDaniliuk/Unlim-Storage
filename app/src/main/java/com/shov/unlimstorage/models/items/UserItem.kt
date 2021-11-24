package com.shov.unlimstorage.models.items

import android.os.Parcel
import android.os.Parcelable

data class User(
	val email: String,
	val role: String,
	val name: String,
	val photoLink: String? = null
):Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString().toString(),
		parcel.readString().toString(),
		parcel.readString().toString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(email)
		parcel.writeString(role)
		parcel.writeString(name)
		parcel.writeString(photoLink)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<User> {
		override fun createFromParcel(parcel: Parcel): User {
			return User(parcel)
		}

		override fun newArray(size: Int): Array<User?> {
			return arrayOfNulls(size)
		}
	}
}
