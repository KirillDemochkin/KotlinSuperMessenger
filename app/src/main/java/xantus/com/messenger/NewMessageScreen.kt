package xantus.com.messenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.setContentView

class NewMessageScreen : AppCompatActivity() {
    lateinit var chatID : String
    lateinit var toUID : String
    lateinit var chatDelay: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = NewMessageScreenUI()
        ui.setContentView(this)
        chatID = intent.getStringExtra("chatID") ?: ""
        toUID = intent.getStringExtra("toUID") ?: ""
        chatDelay = intent.getStringExtra("chatDelay") ?: ""
    }
}
