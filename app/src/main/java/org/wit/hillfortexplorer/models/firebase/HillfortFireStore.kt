package org.wit.hillfortexplorer.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfortexplorer.helpers.readImageFromPath
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.HillfortStore
import org.wit.hillfortexplorer.models.HillfortUserStats
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class HillfortFireStore(val context: Context) : HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun findById(id: Long): HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find { p -> p.id == id }
        return foundHillfort
    }

    override fun create(hillfort: HillfortModel) {
        val key = db.child("users").child(userId).child("hillforts").push().key
        key?.let {
            hillfort.fbId = key
            hillforts.add(hillfort)
            db.child("users").child(userId).child("hillforts").child(key).setValue(hillfort)
            updateImage(hillfort)
        }
    }

    override fun update(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.fbId == hillfort.fbId }
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.additionalNotes = hillfort.additionalNotes
            foundHillfort.isVisited = hillfort.isVisited
            foundHillfort.dateVisited = hillfort.dateVisited
            foundHillfort.images = hillfort.images
            foundHillfort.location = hillfort.location
        }

        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
        updateImage(hillfort)

    }

    override fun delete(hillfort: HillfortModel) {
        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
        hillforts.remove(hillfort)

        hillfort.images.forEach { image ->
            val fileName = File(image)
            val imageName = fileName.getName()
            var imageRef = st.child("${userId}/${imageName}")
            imageRef.delete().addOnSuccessListener {

            }.addOnFailureListener {
                println(it.message)
            }
        }
    }

    override fun clear() {
        hillforts.clear()
    }

    override fun getUserStatistics(userId: String): HillfortUserStats {
        val stats = HillfortUserStats()
        val userHillforts = findAll()

        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        val thisMonth = Calendar.getInstance().get(Calendar.MONTH)

        stats.totalNumberOfHillforts = userHillforts.size
        stats.visitedHillforts = userHillforts.count { p -> p.isVisited }
        stats.visitedThisYear = userHillforts.count { p -> p.dateVisited != null && p.dateVisited?.year == thisYear }
        stats.visitedThisMonth = userHillforts.count { p -> p.dateVisited != null && p.dateVisited?.year == thisYear && p.dateVisited?.month == thisMonth }

        return stats
    }

    fun updateImage(hillfort: HillfortModel) {
        if (hillfort.images.size > 0) {
            val newImagesList = ArrayList(hillfort.images)

            hillfort.images.forEach {image ->
                val fileName = File(image)
                val imageName = fileName.getName()

                var imageRef = st.child("${userId}/${imageName}")
                val baos = ByteArrayOutputStream()
                val bitmap = readImageFromPath(context, image)

                bitmap?.let {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()
                    val uploadTask = imageRef.putBytes(data)
                    uploadTask.addOnFailureListener {
                        println(it.message)
                    }.addOnSuccessListener { taskSnapshot ->
                        taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                            val index = hillfort.images.indexOf(image)
                            newImagesList[index] = it.toString()
                        }
                    }
                }
            }

            hillfort.images = newImagesList
            db.child("users").child(userId).child("hillforts").child(hillfort.fbId)
                .setValue(hillfort)
        }
    }

    fun fetchHillforts(hillfortsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(hillforts) { it.getValue<HillfortModel>(HillfortModel::class.java) }
                hillfortsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        hillforts.clear()
        db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
    }
}