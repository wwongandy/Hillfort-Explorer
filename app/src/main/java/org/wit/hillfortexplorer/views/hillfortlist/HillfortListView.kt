package org.wit.hillfortexplorer.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.models.HillfortAdapter
import org.wit.hillfortexplorer.models.HillfortListener
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.views.BaseView
import org.wit.hillfortexplorer.views.VIEW

class HillfortListView : BaseView(), HillfortListener {

    lateinit var presenter: HillfortListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        init(toolbar)

        presenter = (initPresenter(HillfortListPresenter(this))) as HillfortListPresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.doLoadHillforts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.doLoadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    override fun showHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}