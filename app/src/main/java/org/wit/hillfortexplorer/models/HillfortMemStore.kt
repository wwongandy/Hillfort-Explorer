package org.wit.hillfortexplorer.models

class HillfortMemStore: HillfortStore {

    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun create(hillfort: HillfortModel) {
        hillforts.add(hillfort)
    }
}