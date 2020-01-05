package org.wit.hillfortexplorer.views.hillfortlist

import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.views.BasePresenter
import org.wit.hillfortexplorer.views.BaseView
import org.wit.hillfortexplorer.views.VIEW

class HillfortListPresenter(view: BaseView): BasePresenter(view) {

    lateinit var hillforts: List<HillfortModel>

    init {
        app = view.application as MainApp
    }

    fun doLoadHillforts() {
        doAsync {
            hillforts = app.hillforts.findAll()

            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }

    override fun doOptionsItemSelected(item: MenuItem) {
        when (item?.itemId) {
            R.id.item_add -> view?.navigateTo(VIEW.HILLFORT, 0)
            R.id.item_map -> view?.navigateTo(VIEW.HILLFORTSMAP)
            R.id.item_settings -> view?.navigateTo(VIEW.SETTINGS)
            R.id.item_logout -> doLogout()
        }
    }

    fun doFilterHillforts(query: String, favouriteOnly: Boolean) {
        var tempHillforts: ArrayList<HillfortModel> = ArrayList()

        hillforts.forEach {
            if (it.title.contains(query)) {
                if (favouriteOnly) {
                    if (it.favourite) {
                        tempHillforts.add(it)
                    }
                } else {
                    tempHillforts.add(it)
                }
            }
        }

        view?.showHillforts(tempHillforts)
        view?.recyclerView?.adapter?.notifyDataSetChanged()
    }
}