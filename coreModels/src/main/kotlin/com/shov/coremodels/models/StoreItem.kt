package com.shov.coremodels.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StoreItem(
    @PrimaryKey val id: String = "",
    @ColumnInfo val type: ItemType = ItemType.FOLDER,
    @ColumnInfo val name: String = "",
    @ColumnInfo val disk: StorageType = StorageType.GOOGLE,
    @ColumnInfo val size: String? = null,
    @ColumnInfo val parentFolder: String? = null
)

@Entity
data class DownloadedItem(
    @PrimaryKey val id: String = "",
    @ColumnInfo val name: String = "",
    @ColumnInfo val disk: StorageType = StorageType.GOOGLE,
    @ColumnInfo val downloadedId: Long = 0L,
)
