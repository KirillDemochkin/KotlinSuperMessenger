package xantus.com.messenger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

import com.google.firebase.database.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.editText
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast
import java.net.URL

class LocationActivity : AppCompatActivity() {
    private val geocodingAPIKey : String = "AIzaSyCAjiftf6fvrYi9ymW_d3pdI-weBVbN4Zo"
    private val geocodingBaseAPI : String = "https://maps.googleapis.com/maps/api/geocode/json?address="
    lateinit var databaseRef: DatabaseReference
    lateinit var addressField : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = LocationUI()
        ui.setContentView(this)
        databaseRef = FirebaseDatabase.getInstance().reference
        addressField = ui.userLocation
    }

    fun locateUser(rawAddress : String) = async(UI){
        toast("Locating...")
        val builtAddress = buildAddress(rawAddress)
        val job = bg{
            URL(geocodingBaseAPI + builtAddress + "&key=" + geocodingAPIKey).readText()
        }
        val parser = Parser()
        val stringBuilder = StringBuilder(job.await())
        val downloadedJson = parser.parse(stringBuilder) as JsonObject
        val userCoords = parseJsonForCoords(downloadedJson)

        userCoords?.let{
            createUserAndSaveToFirebase(userCoords)
            setResults(userCoords)
        }
    }

    private fun buildAddress(rawAddress: String) : String{
        val bits = rawAddress.split(" ", ",")
        return bits.joinToString("+")
    }

    private fun createUserAndSaveToFirebase(coords: Coords){
        val userName = intent?.getStringExtra("uid")
        if(userName != null){
            val key = databaseRef.child("users").push().key
            val newUser = UserModel(userName, key, coords, addressField.text.toString())
            databaseRef.child("users").child(key).setValue(newUser)
        }

    }

    private fun setResults(coords : Coords){
        val data = Intent()
        data.putExtra("coords", coords)
        setResult(RESULT_OK, data)
        finish()
    }

    private fun parseJsonForCoords(json: JsonObject) : Coords? {
        val results = json?.get("results") as JsonArray<*>
        val jsonData = results?.get(0) as JsonObject
        val geometry = jsonData?.get("geometry") as JsonObject
        val coords = geometry?.get("location") as JsonObject
        val coordPair = Coords(coords?.get("lat") as Double, coords?.get("lng") as Double)
        return if (coordPair.lat == null || coordPair.lng == null) null else coordPair
    }
}
