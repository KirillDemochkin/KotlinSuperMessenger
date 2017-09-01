package xantus.com.messenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.ctx
import org.jetbrains.anko.setContentView

class MessageHistory : AppCompatActivity() {
    private lateinit var mDBRef : DatabaseReference
    lateinit var chatID : String
    lateinit var toUID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = MessageHistoryUI()
        ui.setContentView(this)
        mDBRef = FirebaseDatabase.getInstance().reference
        chatID = intent.getStringExtra("chatID") ?: ""
        toUID = intent.getStringExtra("toUID") ?: ""
        val adapter = object : FirebaseListAdapter<ChatModel>(ctx, ChatModel::class.java, android.R.layout.simple_list_item_1, mDBRef.child("chats").child(chatID)){
            override fun populateView(v: View?, model: ChatModel?, position: Int) {
                val messagesAsList = model?.messages?.toList()
                val tView1 = v?.findViewById<TextView>(android.R.id.text1)
                if(model?.messages?.isEmpty() != true){tView1?.text = messagesAsList?.get(position)?.second}
            }

        }
        ui.lView.adapter = adapter
    }

}
