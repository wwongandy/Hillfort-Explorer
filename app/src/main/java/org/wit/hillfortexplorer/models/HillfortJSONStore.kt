//package org.wit.hillfortexplorer.models
//
//import android.content.Context
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import com.google.gson.reflect.TypeToken
//import org.jetbrains.anko.AnkoLogger
//import org.wit.hillfortexplorer.helpers.*
//import java.util.*
//import kotlin.collections.ArrayList
//
//val JSON_FILE_HILLFORTS = "hillforts.json"
//val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
//val hillfortsListType = object : TypeToken<java.util.ArrayList<HillfortModel>>(){}.type
//
//fun generateRandomId(): Long {
//    return Random().nextLong()
//}
//
//class HillfortJSONStore : HillfortStore, AnkoLogger {
//
//    val context: Context
//    var hillforts = mutableListOf<HillfortModel>()
//
//    constructor(context: Context) {
//        this.context = context
//
//        if (exists(context, JSON_FILE_HILLFORTS)) {
//            deserialize()
//        }
//    }
//
//    override fun findAll(userId: String): List<HillfortModel> {
//        var userHillforts = ArrayList<HillfortModel>()
//
//        hillforts.forEach {
//            p -> p.userId == userId && userHillforts.add(p)
//        }
//
//        return userHillforts
//    }
//
//    override fun findById(hillfortId: Long): HillfortModel? {
//        val foundHillfort: HillfortModel ?= hillforts.find { p -> p.id == hillfortId }
//        return foundHillfort
//    }
//
//    override fun create(hillfort: HillfortModel, userId: String) {
//        hillfort.userId = userId
//        hillfort.id = generateRandomId()
//        hillforts.add(hillfort)
//
//        serialize()
//    }
//
//    override fun update(hillfort: HillfortModel, userId: String) {
//        var foundHillfort: HillfortModel ?= hillforts.find { p -> p.id == hillfort.id && p.userId == userId }
//
//        if (foundHillfort != null) {
//            foundHillfort.title = hillfort.title
//            foundHillfort.description = hillfort.description
//            foundHillfort.additionalNotes = hillfort.additionalNotes
//            foundHillfort.isVisited = hillfort.isVisited
//            foundHillfort.dateVisited = if (foundHillfort.isVisited) { hillfort.dateVisited } else { null }
//            foundHillfort.images = hillfort.images
//            foundHillfort.location = hillfort.location
//        }
//
//        serialize()
//    }
//
//    override fun delete(hillfort: HillfortModel) {
//        hillforts.remove(hillfort)
//
//        serialize()
//    }
//
//    override fun clear() {
//        hillforts.clear()
//    }
//
//    override fun getUserStatistics(userId: String): HillfortUserStats {
//        val stats = HillfortUserStats()
//        val userHillforts = findAll(userId)
//
//        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
//        val thisMonth = Calendar.getInstance().get(Calendar.MONTH)
//
//        stats.totalNumberOfHillforts = userHillforts.size
//        stats.visitedHillforts = userHillforts.count { p -> p.isVisited }
//        stats.visitedThisYear = userHillforts.count { p -> p.dateVisited != null && p.dateVisited?.year == thisYear }
//        stats.visitedThisMonth = userHillforts.count { p -> p.dateVisited != null && p.dateVisited?.year == thisYear && p.dateVisited?.month == thisMonth }
//
//        return stats
//    }
//
//    private fun serialize() {
//        val jsonString = gsonBuilder.toJson(hillforts, hillfortsListType)
//        write(context, JSON_FILE_HILLFORTS, jsonString)
//    }
//
//    private fun deserialize() {
//        val jsonString = read(context, JSON_FILE_HILLFORTS)
//        hillforts = Gson().fromJson(jsonString, hillfortsListType)
//    }
//}