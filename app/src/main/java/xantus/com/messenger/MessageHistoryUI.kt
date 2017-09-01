package xantus.com.messenger

import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import org.jetbrains.anko.*
import android.widget.LinearLayout.HORIZONTAL
import org.jetbrains.anko.sdk25.coroutines.onClick

class MessageHistoryUI : AnkoComponent<MessageHistory>{
    lateinit var lView : ListView
    override fun createView(ui: AnkoContext<MessageHistory>): View = with(ui) {
        verticalLayout{
            lparams(width = matchParent, height = matchParent)

            lView = listView{
                onItemClickListener = AdapterView.OnItemClickListener { p0, _, p2, _ ->
                    val item = p0?.getItemAtPosition(p2) as UserModel
                    item?.let{
                        toast(item.userID)
                        startActivity<MessageHistory>("uid" to item.userID)
                    }
                }
            }.lparams(width = matchParent, height = matchParent)

            linearLayout{
                orientation = HORIZONTAL
                button{
                    textResource = R.string.new_letter_button
                    onClick {
                        startActivity<NewMessageScreen>("chatID" to ui.owner.chatID, "toUID" to ui.owner.toUID)
                    }
                }.lparams(width = matchParent, height = wrapContent)

            }.lparams(width = matchParent, height = wrapContent)
        }
    }

}