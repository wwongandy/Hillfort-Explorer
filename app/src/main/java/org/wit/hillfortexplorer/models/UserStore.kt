package org.wit.hillfortexplorer.models

interface UserStore {

    fun ensureUniqueCredentials(username: String, password: String)
    fun register(username: String, password: String)
}