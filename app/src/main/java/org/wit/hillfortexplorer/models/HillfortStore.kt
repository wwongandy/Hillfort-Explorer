package org.wit.hillfortexplorer.models

interface HillfortStore {

    // userId used to differentiate Hillforts belonging to different users
    fun findAll(userId: String): List<HillfortModel>
    fun findById(hillfortId: Long): HillfortModel?
    fun create(hillfort: HillfortModel, userId: String)
    fun update(hillfort: HillfortModel, userId: String)
    fun delete(hillfort: HillfortModel)

    fun getUserStatistics(userId: String): HillfortUserStats
}