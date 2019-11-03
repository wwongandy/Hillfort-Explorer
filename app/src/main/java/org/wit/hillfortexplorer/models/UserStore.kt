package org.wit.hillfortexplorer.models

interface UserStore {

    fun ensureUniqueCredentials(username: String, password: String): Boolean
    fun register(username: String, password: String): Boolean
    fun authenticate(username: String, password: String): Boolean
    fun session(username: String): UserModel?
}