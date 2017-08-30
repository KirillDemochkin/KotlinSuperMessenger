package xantus.com.messenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.ctx
import org.jetbrains.anko.setContentView

class MessageHistory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = MessageHistoryUI()
        ui.setContentView(this)
        val chatID = ""
        val adapter = object : FirebaseListAdapter<MessageModel>(ctx, MessageModel::class.java, android.R.layout.simple_list_item_1, FirebaseDatabase.getInstance().reference.child("chats").child(chatID)){
            override fun populateView(v: View?, model: MessageModel?, position: Int) {

            }

        }
    }

}
