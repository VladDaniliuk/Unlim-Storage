package com.shov.unlimstorage.models.items

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shov.unlimstorage.models.repositories.signIn.StorageType

enum class ItemType(val imageVector: ImageVector) {
	FILE(Icons.Rounded.Description), FOLDER(Icons.Rounded.Folder);
}

@Entity
data class StoreItem(
	@PrimaryKey val id: String,
	@ColumnInfo val type: ItemType,
	@ColumnInfo val name: String,
	@ColumnInfo val size: String? = null,
	@ColumnInfo val parentFolder: String? = null,
	@ColumnInfo val disk: StorageType
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString().toString(),
		ItemType.valueOf(parcel.readString().toString()),
		parcel.readString().toString(),
		parcel.readString(),
		parcel.readString(),
		StorageType.valueOf(parcel.readString().toString())
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(id)
		parcel.writeString(type.name)
		parcel.writeString(name)
		parcel.writeString(size)
		parcel.writeString(parentFolder)
		parcel.writeString(disk.name)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<StoreItem> {
		override fun createFromParcel(parcel: Parcel): StoreItem {
			return StoreItem(parcel)
		}

		override fun newArray(size: Int): Array<StoreItem?> {
			return arrayOfNulls(size)
		}
	}
}
