package xantus.com.messenger

import android.view.View
import com.firebase.ui.database.FirebaseListAdapter
import org.jetbrains.anko.*
import android.R.layout
import android.widget.AdapterView
import android.widget.ListView
import com.firebase.ui.auth.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.abc_alert_dialog_material.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.threeten.bp.LocalDateTime

/**
 * Created by dravk on 8/25/2017.
 */
class ConversationsUI : AnkoComponent<ConversationsOverview>{
    lateinit var lView : ListView
    override fun createView(ui: AnkoContext<ConversationsOverview>): View = with(ui){
        verticalLayout{
            lparams(width = matchParent, height = matchParent)
           // val tmp = textView{visibility = View.GONE}.lparams(width = dip(0), height = dip(0))
            val singleUserMap = mutableMapOf<String, UserModel>()
            val singleChatMap = mutableMapOf<String, String>()
            lView = listView{
                onItemClickListener = AdapterView.OnItemClickListener { p0, _, p2, _ ->
                    val item = p0?.getItemAtPosition(p2) as UserModel
                    val ref = FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser?.uid)
                    ref.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(p0: DataSnapshot?) {

                            val user = p0?.getValue(UserModel::class.java)?: UserModel()
                            singleUserMap.put("user", user)
                        }

                        override fun onCancelled(p0: DatabaseError?) {
                            toast("Something went wrong :(")
                        }

                    })

                    val currentUser = singleUserMap["user"]
                    currentUser?.let {

                            //toast(item.userID)
                            if (!currentUser.contacts.containsKey(item.userID)) {
                                ui.owner.registerContact(currentUser, item)
                            }
                            val chatID: String = currentUser.contacts[item.userID] ?: ""
                            val refChats = FirebaseDatabase.getInstance().reference.child("chats").child(chatID).child("deliveryTime")
                            refChats.addListenerForSingleValueEvent(object: ValueEventListener{
                                override fun onCancelled(p0: DatabaseError?) {

                                }

                                override fun onDataChange(p0: DataSnapshot?) {
                                    singleChatMap.put("chatDelay", p0?.value.toString() )
                                    startActivity<MessageHistory>("chatID" to chatID, "toUID" to item.userID, "chatDelay" to p0?.value.toString())
                                }

                            })
                            //val chatDelay = singleChatMap["chatDelay"]?: ""
                            //toast(chatDelay)


                    }
                }
            }.lparams(width = matchParent, height = matchParent, weight = 1f)
            button{
                textResource = R.string.conversations_refresh_button
                onClick {
                    var currentUser: UserModel
                    if (singleUserMap.isEmpty()) {
                        val ref = FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser?.uid)
                        ref.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot?) {

                                val user = p0?.getValue(UserModel::class.java) ?: UserModel()
                                singleUserMap.put("user", user)
                            }

                            override fun onCancelled(p0: DatabaseError?) {
                                toast("Something went wrong :(")
                            }

                        })


                    }
                    currentUser = singleUserMap["user"] ?: UserModel()
                    val lastCheckTime = LocalDateTime.parse(currentUser.lastUpdate)
                    if (LocalDateTime.now().isAfter(lastCheckTime.plusHours(12)) || true) {
                        toast("Refreshing!")
                        for ((_, chatID) in currentUser.contacts) {
                            val ref = FirebaseDatabase.getInstance().reference.child("chats").child(chatID)

                            ref.child("pendingMessages").addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError?) {

                                }

                                override fun onDataChange(data: DataSnapshot?) {
                                    val gti = GenericTypeIndicator<MutableMap<String, MessagePairModel>>()
                                    val pendingMessages = data?.value as MutableMap<String, MessagePairModel>
                                    val pendingMessagesCopy = mutableMapOf<String, MessagePairModel>()
                                    pendingMessages?.let {
                                        val mesRef = FirebaseDatabase.getInstance().reference.child("messages")
                                        for ((messageID, _) in pendingMessages) {
                                            //toast(messageID)
                                            mesRef.child(messageID).addListenerForSingleValueEvent(object : ValueEventListener {
                                                override fun onCancelled(p0: DatabaseError?) {

                                                }

                                                override fun onDataChange(p0: DataSnapshot?) {

                                                    val message = p0?.getValue(MessageModel::class.java) ?: MessageModel()
                                                    //toast(message.body.subject)
                                                    if (isAvailable(message.timeAvailable)) {
                                                        ref.child("messages").child(messageID).setValue(MessagePairModel(messageID, message.body.subject))
                                                        toast("added message")
                                                    } else{
                                                        pendingMessagesCopy.put(messageID, MessagePairModel(messageID, message.body.subject))
                                                    }
                                                }

                                            })
                                        }
                                    }
                                    ref.child("pendingMessages").setValue(pendingMessagesCopy)
                                }
                            })
                        }
                        FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser?.uid).child("lastUpdate").setValue(LocalDateTime.now().toString())
                    }
                }
            }.lparams(width = matchParent, height = wrapContent)
        }

    }



}