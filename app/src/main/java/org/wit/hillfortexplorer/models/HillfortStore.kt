package org.wit.hillfortexplorer.models

interface HillfortStore {

    // userId used to differentiate Hillforts belonging to different users
    fun findAll(): List<HillfortModel>
    fun findById(hillfortId: Long): HillfortModel?
    fun create(hillfort: HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)
    fun clear()

    fun getUserStatistics(userId: String): HillfortUserStats
}