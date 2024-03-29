package xantus.com.messenger

import com.google.firebase.database.FirebaseDatabase
import org.threeten.bp.LocalDateTime

class UserModel(val username : String = "", val userID : String = "", val coords : Coords = Coords(0.0, 0.0), val location : String = ""){
    val contacts = mutableMapOf<String, String>()
    var lastUpdate: String = ""
    fun setupContacts() {
        val ref = FirebaseDatabase.getInstance().reference
        val key = userID
        val value = ref.child("chats").push().key
        val newChat = ChatModel(value, key, key)
        newChat.deliveryTime = 0
        ref.child("chats").child(value).setValue(newChat)
        contacts.put(key, value)
    }

}
