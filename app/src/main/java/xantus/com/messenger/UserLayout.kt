package xantus.com.messenger


import android.app.Fragment
import android.view.View
import android.widget.LinearLayout.HORIZONTAL
import org.jetbrains.anko.*

/**
 * Created by dravk on 8/27/2017.
 */
class UserLayout : AnkoComponent<Fragment>{
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui){

        linearLayout{
            lparams(height = matchParent, width = matchParent)
            orientation = HORIZONTAL
            editText{
                id = R.id.userNameEditText
            }.lparams(height = wrapContent, width = wrapContent)
            editText{
                id = R.id.lastMessageText
            }.lparams(height = wrapContent, width = wrapContent)
        }

    }

}