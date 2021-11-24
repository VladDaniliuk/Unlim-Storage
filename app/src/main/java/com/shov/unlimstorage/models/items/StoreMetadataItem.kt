package com.shov.unlimstorage.models.items

import android.os.Parcel
import android.os.Parcelable
import kotlinx.datetime.LocalDateTime

data class StoreMetadataItem(
	val id: String,
	val name: String,
	val type: ItemType,
	var description: String?,
	val isStarred: Boolean = false,
	val version: String?,
	val link: String?,
	val createdTime: LocalDateTime?,
	val modifiedTime: LocalDateTime?,
	val sharingUsers: List<User>? = null,
	val size: String?
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString().toString(),
		parcel.readString().toString(),
		ItemType.valueOf(parcel.readString().toString()),
		parcel.readString(),
		parcel.readByte() != 0.toByte(),
		parcel.readString(),
		parcel.readString(),
		LocalDateTime.parse(parcel.readString().toString()),
		LocalDateTime.parse(parcel.readString().toString()),
		arrayListOf<User>().apply {
			parcel.readList(
				this,
				User::javaClass.javaClass.classLoader
			)
		},
		parcel.readString()
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(id)
		parcel.writeString(name)
		parcel.writeString(description)
		parcel.writeByte(if (isStarred) 1 else 0)
		parcel.writeString(version)
		parcel.writeString(link)
		parcel.writeString(createdTime.toString())
		parcel.writeString(modifiedTime.toString())
		parcel.writeList(sharingUsers)
		parcel.writeString(size)
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
