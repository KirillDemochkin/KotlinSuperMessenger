package xantus.com.messenger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime

/**
 * Created by dravk on 8/29/2017.
 */
@Parcelize
class MessageModel(val userFrom : String = "",
                   val userTo : String = "",
                   val chatID: String = "",
                   val body : MessageContainer = MessageContainer(),
                   val messageID : String = "") : Parcelable{
    var timeAvailable = LocalDateTime.now().toString() //add offset
}