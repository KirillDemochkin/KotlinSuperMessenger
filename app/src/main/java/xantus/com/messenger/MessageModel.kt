package xantus.com.messenger

import org.threeten.bp.LocalDateTime

/**
 * Created by dravk on 8/29/2017.
 */

class MessageModel(val userFrom : String = "",
                   val userTo : String = "",
                   val chatID: String = "",
                   val body : MessageContainer = MessageContainer(),
                   val messageID : String = ""){
    var timeAvailable = LocalDateTime.now()
}