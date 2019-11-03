package org.wit.hillfortexplorer.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfortexplorer.helpers.exists
import org.wit.hillfortexplorer.helpers.read
import org.wit.hillfortexplorer.helpers.write
import java.util.ArrayList

val JSON_FILE_USERS = "users.json"
val usersListType = object : TypeToken<ArrayList<UserModel>>(){}.type

class UserJSONStore: UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    constructor(context: Context) {
        this.context = context

        if (exists(context, JSON_FILE_USERS)) {
            deserialize()
        }
    }

    override fun ensureUniqueCredentials(username: String, password: String): Boolean {
        var foundUser: UserModel ?= users.find { u -> u.username == username }
        return foundUser == null
    }

    override fun register(username: String, password: String): Boolean {
        val uniqueCredentials = ensureUniqueCredentials(username, password)

        if (!uniqueCredentials) {
            return false
        }

        val newUser = UserModel(generateRandomId(), username, password)
        users.add(newUser)
        serialize()

        return true
    }

    override fun authenticate(username: String, password: String): Boolean {
        var foundUser: UserModel ?= users.find { u -> u.username == username && u.password == password }
        return foundUser != null
    }

    override fun session(username: String): UserModel? {
        var foundUser: UserModel ?= users.find { u -> u.username == username }
        return foundUser
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(users, usersListType)
        write(context, JSON_FILE_USERS, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE_USERS)
        users = Gson().fromJson(jsonString, usersListType)
    }
}