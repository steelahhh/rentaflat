package io.github.steelahhh.rent.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Entity(tableName = "Flats")
@TypeConverters(FlatTypeConverter::class)
data class Flat(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val address: String = "",
    val area: Double = 0.0,
    val price: Long = 0,
    val rooms: Int = 0,
    val pricePerSqM: Double = 0.0,
    val floor: Int = 0,
    val floors: Int = 0
)