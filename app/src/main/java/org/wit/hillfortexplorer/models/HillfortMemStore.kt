package org.wit.hillfortexplorer.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class HillfortMemStore: HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun create(hillfort: HillfortModel) {
        hillforts.add(hillfort)
        logAll()
    }

    fun logAll() {
        hillforts.forEach{ info("${it}") }
    }
}