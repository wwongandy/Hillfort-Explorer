package org.wit.hillfortexplorer.models.room

import androidx.room.*
import org.wit.hillfortexplorer.models.HillfortModel

@Dao
interface HillfortDao {

    @Query("SELECT * FROM HillfortModel")
    fun findAll(): List<HillfortModel>

    @Query("select * from HillfortModel where id = :hillfortId")
    fun findById(hillfortId: Long): HillfortModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(hillfort: HillfortModel)

    @Update
    fun update(hillfort: HillfortModel)

    @Delete
    fun delete(hillfort: HillfortModel)
}