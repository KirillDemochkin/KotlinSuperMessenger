package xantus.com.messenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.*
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

class MessageCloseUp : AppCompatActivity() {


    lateinit var dbRef: DatabaseReference
    lateinit var mesDisplay : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = MessageCloseUpUI()
        ui.setContentView(this)
        val messageID = intent.getStringExtra("messageID")
        dbRef = FirebaseDatabase.getInstance().reference.child("messages").child(messageID)
        mesDisplay = ui.mesDisplay
        loadMessage()
    }

    private fun loadMessage(){

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                val message = p0?.getValue(MessageModel::class.java) ?: MessageModel()
                val messageBody = message.body
                val mesText = "To: ${messageBody.to}\n\nAbout: ${messageBody.subject}\n\n${messageBody.body}\n${messageBody.from}"
                mesDisplay.text = mesText
            }
            override fun onCancelled(p0: DatabaseError?) {
                toast("message cancelled")
            }

        })

    }
}
