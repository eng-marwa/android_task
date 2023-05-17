package me.marwa.androidtask.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("price")
    var price: Double? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("category")
    var category: String? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("rating")
    var rating: Rating? = Rating()
) : Parcelable

@Parcelize
data class Rating(

    @SerializedName("rate") var rate: Double? = null,
    @SerializedName("count") var count: Int? = null

) : Parcelable