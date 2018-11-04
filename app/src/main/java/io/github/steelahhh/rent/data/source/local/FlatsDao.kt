package io.github.steelahhh.rent.data.source.local

import androidx.room.*
import io.github.steelahhh.rent.model.Flat
import io.reactivex.Maybe
import io.reactivex.Single

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

@Dao
interface FlatsDao {

    @Query("SELECT * FROM Flats")
    fun getFlats(): Single<List<Flat>>

    @Query("SELECT * FROM Flats WHERE id = :id")
    fun getFlat(id: Int): Maybe<Flat>

    @Query("DELETE FROM Flats WHERE id = :id")
    fun deleteFlat(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFlat(flat: Flat): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFlats(flat: List<Flat>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFlat(flat: Flat)

    @Query("DELETE FROM Flats")
    fun deleteAllFlats(): Int

    @get:Query("SELECT COUNT(*) FROM flats")
    val flatCount: Single<Int>

}
