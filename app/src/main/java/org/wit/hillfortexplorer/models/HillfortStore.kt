package org.wit.hillfortexplorer.models

interface HillfortStore {

    // userId used to differentiate Hillforts belonging to different users
    fun findAll(userId: Long): List<HillfortModel>
    fun findById(hillfortId: Long): HillfortModel?
    fun create(hillfort: HillfortModel, userId: Long)
    fun update(hillfort: HillfortModel, userId: Long)
    fun delete(hillfort: HillfortModel)

    fun getUserStatistics(userId: Long): HillfortUserStats
}