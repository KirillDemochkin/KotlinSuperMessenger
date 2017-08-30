package xantus.com.messenger

import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import org.jetbrains.anko.*
import android.widget.LinearLayout.HORIZONTAL

class MessageHistoryUI : AnkoComponent<MessageHistory>{
    lateinit var lView : ListView
    override fun createView(ui: AnkoContext<MessageHistory>): View = with(ui) {
        verticalLayout{
            lparams(width = matchParent, height = matchParent)

            lView = listView{
                onItemClickListener = object : AdapterView.OnItemClickListener{
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val item = p0?.getItemAtPosition(p2) as UserModel
                        item?.let{
                            toast(item.userID)
                            startActivity<MessageHistory>("uid" to item.userID)
                        }
                    }

                }
            }.lparams(width = matchParent, height = matchParent)

            linearLayout{
                orientation = HORIZONTAL
                button{

                }
                button{

                }
            }.lparams(width = matchParent, height = wrapContent)
        }
    }

}