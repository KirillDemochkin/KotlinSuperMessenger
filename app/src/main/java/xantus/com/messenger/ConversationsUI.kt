package xantus.com.messenger

import android.view.View
import com.firebase.ui.database.FirebaseListAdapter
import org.jetbrains.anko.*
import android.R.layout
import android.widget.AdapterView
import android.widget.ListView
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by dravk on 8/25/2017.
 */
class ConversationsUI : AnkoComponent<ConversationsOverview>{
    lateinit var lView : ListView
    override fun createView(ui: AnkoContext<ConversationsOverview>): View = with(ui){
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
        }
    }

}