package org.wit.hillfortexplorer.models

interface UserStore {

    fun ensureUniqueCredentials(username: String, password: String): Boolean
    fun register(username: String, password: String): Boolean
    fun authenticate(username: String, password: String): UserModel

    fun changeUsername(username: String, password: String, newUsername: String)
    fun changePassword(username: String, password: String, newPassword: String)
}