package xantus.com.messenger

/**
 * Created by dravk on 8/30/2017.
 */
class ChatModel(val chatID: String = "", val User1 : String = "", val user2 : String = ""){
    val messages = mutableMapOf<String, String>()
    var deliveryTime : Int = 0

    fun calculateDeliveryTime(from: Coords, to: Coords){

    }
}