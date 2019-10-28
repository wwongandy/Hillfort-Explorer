package org.wit.hillfortexplorer.models

interface HillfortStore {

    fun findAll(): List<HillfortModel>
    fun create(hillfort: HillfortModel)
}