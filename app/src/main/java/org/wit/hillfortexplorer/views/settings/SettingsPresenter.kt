package org.wit.hillfortexplorer.views.settings

import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortUserStats
import org.wit.hillfortexplorer.views.BasePresenter
import org.wit.hillfortexplorer.views.BaseView
import org.wit.hillfortexplorer.views.VIEW

class SettingsPresenter(view: BaseView): BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        app = view.application as MainApp
    }

    fun getUserStatistics(): HillfortUserStats {
        return app.hillforts.getUserStatistics(auth.currentUser!!.uid)
    }

    fun doChangeUsername(newUsername: String) {
        auth.currentUser!!.updateEmail(newUsername).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                view?.toast("Email changed successfully")
            } else {
                view?.toast("Email change failed: ${task.exception?.message}")
            }
        }
    }

    fun doChangePassword(newPassword: String) {
        auth.currentUser!!.updatePassword(newPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                view?.toast("Password changed successfully")
            } else {
                view?.toast("Password change failed: ${task.exception?.message}")
            }
        }
    }

    override fun doOptionsItemSelected(item: MenuItem) {
        when (item?.itemId) {
            R.id.item_logout -> view?.navigateTo(VIEW.AUTHENTICATION)
        }
    }
}