package org.wit.hillfortexplorer.models.room

import android.content.Context
import androidx.room.Room
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.HillfortStore
import org.wit.hillfortexplorer.models.HillfortUserStats
import java.util.*

class HillfortStoreRoom(val context: Context) : HillfortStore {

    var dao: HillfortDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.hillfortDao()
    }

    override fun findAll(): List<HillfortModel> {
        return dao.findAll()
    }

    override fun findById(hillfortId: Long): HillfortModel? {
        return dao.findById(hillfortId)
    }

    override fun create(hillfort: HillfortModel) {
        dao.create(hillfort)
    }

    override fun update(hillfort: HillfortModel) {
        dao.update(hillfort)
    }

    override fun delete(hillfort: HillfortModel) {
        dao.delete(hillfort)
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserStatistics(userId: String): HillfortUserStats {
        val stats = HillfortUserStats()
        val userHillforts = findAll()

        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        val thisMonth = Calendar.getInstance().get(Calendar.MONTH)

        stats.totalNumberOfHillforts = userHillforts.size
        stats.visitedHillforts = userHillforts.count { p -> p.isVisited }
        stats.favouritedHillforts = userHillforts.count { p -> p.favourite }
        stats.visitedThisYear = userHillforts.count { p -> p.dateVisited != null && p.dateVisited?.year == thisYear }
        stats.visitedThisMonth = userHillforts.count { p -> p.dateVisited != null && p.dateVisited?.year == thisYear && p.dateVisited?.month == thisMonth }

        return stats
    }
}