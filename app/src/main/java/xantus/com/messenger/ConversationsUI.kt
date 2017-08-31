package xantus.com.messenger

import android.view.View
import com.firebase.ui.database.FirebaseListAdapter
import org.jetbrains.anko.*
import android.R.layout
import android.widget.AdapterView
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.sdk25.coroutines.onClick

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
                        item?.let {
                            toast(item.userID)
                            if (!currentUser.contacts.containsKey(item.userID)) {
                                ui.owner.registerContact(currentUser, item)
                            }
                            val chatID: String = currentUser.contacts[item.userID] ?: ""
                            startActivity<MessageHistory>("uid" to chatID)
                        }
                    }
                }
            }.lparams(width = matchParent, height = matchParent)
            button{
                textResource = R.string.conversations_refresh_button
                onClick {

                }
            }.lparams(width = matchParent, height = wrapContent)
        }

    }



}