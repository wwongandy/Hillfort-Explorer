package org.wit.hillfortexplorer.views.authentication

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.models.firebase.HillfortFireStore
import org.wit.hillfortexplorer.views.BasePresenter
import org.wit.hillfortexplorer.views.BaseView
import org.wit.hillfortexplorer.views.VIEW

class AuthenticationPresenter(view: BaseView): BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: HillfortFireStore? = null

    init {
        if (app.hillforts is HillfortFireStore) {
            fireStore = app.hillforts as HillfortFireStore
        }
    }

    fun doRegisterUser(username: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchHillforts {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.HILLFORTLIST)
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.HILLFORTLIST)
                }
            } else {
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
            view?.hideProgress()
        }
    }

    fun doLoginUser(username: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchHillforts {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.HILLFORTLIST)
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.HILLFORTLIST)
                }
            } else {
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
            view?.hideProgress()
        }
    }
}