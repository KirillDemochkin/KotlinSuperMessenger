package xantus.com.messenger

class UserModel(val username : String = "", val userID : String = "", val coords : Coords = Coords(0.0, 0.0), val location : String = ""){
    val contacts = mutableMapOf<String, String>()
}
