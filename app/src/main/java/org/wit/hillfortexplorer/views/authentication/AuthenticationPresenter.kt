package org.wit.hillfortexplorer.views.authentication

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.views.BasePresenter
import org.wit.hillfortexplorer.views.BaseView
import org.wit.hillfortexplorer.views.VIEW

class AuthenticationPresenter(view: BaseView): BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        app = view.application as MainApp
    }

    fun doRegisterUser(username: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                app.currentUser = auth.currentUser!!
                view?.navigateTo(VIEW.HILLFORTLIST)
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
                app.currentUser = auth.currentUser!!
                view?.navigateTo(VIEW.HILLFORTLIST)
            } else {
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
            view?.hideProgress()
        }
    }
}