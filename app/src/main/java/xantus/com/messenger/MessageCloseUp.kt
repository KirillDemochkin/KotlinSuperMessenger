package xantus.com.messenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.database.*
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

class MessageCloseUp : AppCompatActivity() {


    lateinit var dbRef: DatabaseReference
    lateinit var mesDisplay : EditText

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
        val tmp = mutableMapOf<String, MessageModel>()
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                val downloadedMessage = p0?.getValue(MessageModel::class.java) ?: MessageModel()
                tmp.put("message", downloadedMessage)

            }
            override fun onCancelled(p0: DatabaseError?) {
                toast("message cancelled")
            }

        })
        val message = tmp["message"]
        message?.let {
            toast(message.body.subject)
            val messageBody = message.body
            val mesText = "To: ${messageBody.to}\n ${messageBody.subject}\n ${messageBody.body}\nYours truly, ${messageBody.from}"
            mesDisplay.setText(mesText)
        }
    }
}
