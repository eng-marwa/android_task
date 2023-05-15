package me.marwa.androidtask.domain.entity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import android.util.Base64
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import java.io.ByteArrayOutputStream


@Entity
@Parcelize
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int = 0,
    @ColumnInfo
    var cantDeleted: Int = 0,
    @ColumnInfo
    val productId: String = "",
    @ColumnInfo
    val productName: String = "",
    @ColumnInfo
    val productPrice: Double = 0.0,
    @ColumnInfo
    var productDescription: String = "",

    @ColumnInfo
    var category: String = "",
    @TypeConverters(ImageConverter::class)
    @ColumnInfo
    var productImage: Bitmap? = null,
    @ColumnInfo
    var qty: Int = 0,
    @ColumnInfo
    var itemTotal: Double = 0.0,
) : Parcelable

class ImageConverter {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): String {
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)

    }

    @TypeConverter
    fun toBitmap(encodedString: String): Bitmap? {
        val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }
}
