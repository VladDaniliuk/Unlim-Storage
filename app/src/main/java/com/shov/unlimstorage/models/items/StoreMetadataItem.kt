package com.shov.unlimstorage.models.items

import android.os.Parcel
import android.os.Parcelable

data class StoreMetadataItem(
	val id: String,
	val name: String,
	val type: ItemType,
	var description: String? = null,
	val isStarred: Boolean = false,
	val version: String? = null,
	val link: String? = null,
	val createdTime: String? = null,
	val modifiedTime: String? = null,
	val sharingUsers: List<User>? = null,
	val size: Long? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString().toString(),
		parcel.readString().toString(),
		ItemType.valueOf(parcel.readString().toString()),
		parcel.readString(),
		parcel.readByte() != 0.toByte(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		arrayListOf<User>().apply {
			parcel.readList(
				this,
				User::javaClass.javaClass.classLoader
			)
		},
		parcel.readValue(Long::class.java.classLoader) as? Long
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(id)
		parcel.writeString(name)
		parcel.writeString(description)
		parcel.writeByte(if (isStarred) 1 else 0)
		parcel.writeString(version)
		parcel.writeString(link)
		parcel.writeString(createdTime)
		parcel.writeString(modifiedTime)
		parcel.writeList(sharingUsers)
		parcel.writeValue(size)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<StoreMetadataItem> {
		override fun createFromParcel(parcel: Parcel): StoreMetadataItem {
			return StoreMetadataItem(parcel)
		}

		override fun newArray(size: Int): Array<StoreMetadataItem?> {
			return arrayOfNulls(size)
		}
	}
}
