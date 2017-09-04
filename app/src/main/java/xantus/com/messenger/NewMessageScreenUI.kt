package xantus.com.messenger

import android.app.Notification
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.fitWindowsLinearLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by dravk on 8/30/2017.
 */
class NewMessageScreenUI: AnkoComponent<NewMessageScreen>{
    lateinit var fromField : EditText
    lateinit var toField : EditText
    lateinit var subjectField : EditText
    lateinit var bodyField : EditText
    lateinit var dbRef: DatabaseReference
    override fun createView(ui: AnkoContext<NewMessageScreen>): View = with(ui) {
        scrollView {
            lparams(width = matchParent, height = matchParent)
            linearLayout {
                orientation = VERTICAL
                linearLayout {
                    orientation = HORIZONTAL

                    toField = editText {
                        horizontalPadding = dip(1)
                        hintResource = R.string.to
                    }.lparams(width = 0, height = wrapContent, weight = 2f)

                    subjectField = editText {
                        horizontalPadding = dip(1)
                        hintResource = R.string.subject
                    }.lparams(width = 0, height = wrapContent, weight = 3f)
                }.lparams(width = matchParent, height = wrapContent)

                linearLayout {
                    orientation = VERTICAL
                    bodyField = editText {
                        //verticalPadding = dip(5)
                        horizontalPadding = dip(2)
                        hintResource = R.string.message
                    }.lparams(width = matchParent, height = matchParent, weight = 1f)

                    fromField = editText {
                        horizontalPadding = dip(2)
                        hintResource = R.string.from
                    }.lparams(width = matchParent, height = wrapContent)

                    button {
                        textResource = R.string.send_button
                        onClick {
                            dbRef = FirebaseDatabase.getInstance().reference
                            val key = dbRef.child("messages").push().key

                            val mesContainer = MessageContainer(fromField.text.toString(),
                                    toField.text.toString(),
                                    subjectField.text.toString(),
                                    bodyField.text.toString())
                            val newMesForDB = MessageModel(FirebaseAuth.getInstance().currentUser?.uid ?: "UserNotFound",
                                    ui.owner.toUID,
                                    ui.owner.chatID,
                                    mesContainer,
                                    key)
                            dbRef.child("messages").child(key).setValue(newMesForDB)
                            dbRef.child("chats").child(ui.owner.chatID).child("messages").child(key).setValue(MessagePairModel(key, mesContainer.subject))
                            ui.owner.finish()
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                }.lparams(width = matchParent, height = matchParent, weight = 1f)
            }.lparams(width = matchParent, height = matchParent)
        }
    }
}