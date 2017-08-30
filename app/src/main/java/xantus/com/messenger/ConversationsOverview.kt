package xantus.com.messenger

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.User
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.threetenabp.AndroidThreeTen
import org.jetbrains.anko.ctx
import org.jetbrains.anko.longToast
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

class ConversationsOverview : AppCompatActivity() {

    private val SIGN_IN_REQUEST_CODE = 100
    private val LOCATE_USER_REQUEST_CODE = 200
    private lateinit var listView : ListView
    private lateinit var mDBRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidThreeTen.init(this)
        super.onCreate(savedInstanceState)
        val ui = ConversationsUI()
        ui.setContentView(this)
        mDBRef = FirebaseDatabase.getInstance().reference
        listView = ui.lView
        //FirebaseListAdapter(this@ConversationsOverview, UserModel::class.java,)

        if(FirebaseAuth.getInstance().currentUser == null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE)
            val i = Intent(this, LocationActivity::class.java)
            i.putExtra("uid", FirebaseAuth.getInstance().currentUser?.displayName.toString())
            startActivityForResult(i, LOCATE_USER_REQUEST_CODE)
            displayContacts()
        } else{
            toast("Welcome " + FirebaseAuth.getInstance().currentUser?.displayName)
            val i = Intent(this, LocationActivity::class.java)
            i.putExtra("uid", FirebaseAuth.getInstance().currentUser?.displayName.toString())
            startActivityForResult(i, LOCATE_USER_REQUEST_CODE)
            displayContacts()
        }

    }

    private fun displayContacts(){

        val adapter = object : FirebaseListAdapter<UserModel>(ctx, UserModel::class.java, android.R.layout.simple_list_item_2, mDBRef.child("users")){
            override fun populateView(v: View?, model: UserModel?, position: Int) {
                val tView1 = v?.findViewById<TextView>(android.R.id.text1)
                val tView2 = v?.findViewById<TextView>(android.R.id.text2)
                tView1?.text = model?.username
                tView2?.text = model?.location
            }
        }
        listView.adapter = adapter

    }

    fun registerContact(user1 : UserModel, user2 : UserModel){
        val user1Map = user1.contacts
        val user2Map = user2.contacts
        val chatKey = mDBRef.child("chats").push().key
        user1Map.put(user2.userID, chatKey)
        user2Map.put(user1.userID, chatKey)
        val childUpdates = mutableMapOf<String, Any>()
        childUpdates.put("/users/"+user1.userID+"/contacts", user1Map)
        childUpdates.put("/users/"+user2.userID+"/contacts", user2Map)
        mDBRef.updateChildren(childUpdates)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            SIGN_IN_REQUEST_CODE -> {
                if(resultCode == Activity.RESULT_OK){

                } else {
                    toast("Could not sign in :(")
                    finish()
                }
            }
            LOCATE_USER_REQUEST_CODE -> {
                if(resultCode == Activity.RESULT_OK){
                    val userCoords = data?.getParcelableExtra<Coords>("coords")
                    longToast(userCoords.toString())
                }
            }
            else -> return
        }
    }
}
