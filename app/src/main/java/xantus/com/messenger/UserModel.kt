package xantus.com.messenger

import com.google.firebase.database.FirebaseDatabase

class UserModel(val username : String = "", val userID : String = "", val coords : Coords = Coords(0.0, 0.0), val location : String = ""){
    val contacts = mutableMapOf<String, String>()

    fun setupContacts() {
        val ref = FirebaseDatabase.getInstance().reference
        val key = userID
        val value = ref.child("chats").push().key
        ref.child("chats").child(value).setValue(false)
        contacts.put(key, value)
    }

}
