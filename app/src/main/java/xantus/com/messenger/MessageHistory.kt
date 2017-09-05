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
import org.jetbrains.anko.toast

class MessageHistory : AppCompatActivity() {
    private lateinit var mDBRef : DatabaseReference
    lateinit var chatID : String
    lateinit var toUID : String
    lateinit var chatDelay : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = MessageHistoryUI()
        ui.setContentView(this)
        mDBRef = FirebaseDatabase.getInstance().reference
        chatID = intent.getStringExtra("chatID") ?: ""
        toUID = intent.getStringExtra("toUID") ?: ""
        chatDelay = intent.getStringExtra("chatDelay") ?: ""
        //toast(chatDelay)

        val adapter = object : FirebaseListAdapter<MessagePairModel>(ctx, MessagePairModel::class.java, android.R.layout.simple_list_item_1, mDBRef.child("chats").child(chatID).child("messages")){
            override fun populateView(v: View?, model: MessagePairModel?, position: Int) {
                val tView1 = v?.findViewById<TextView>(android.R.id.text1)
                tView1?.text = model?.messageSubject
            }

        }
        ui.lView.adapter = adapter
    }

}
