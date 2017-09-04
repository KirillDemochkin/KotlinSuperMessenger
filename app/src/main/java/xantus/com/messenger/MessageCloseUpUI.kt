package xantus.com.messenger

import android.text.Editable
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by dravk on 9/1/2017.
 */

class MessageCloseUpUI : AnkoComponent<MessageCloseUp>{
    lateinit var mesDisplay : EditText
    override fun createView(ui: AnkoContext<MessageCloseUp>): View = with(ui){
      linearLayout{
          lparams(width = matchParent, height = matchParent)
          mesDisplay = editText{

          }.lparams(width = matchParent, height = wrapContent)
          button{
              textResource = R.string.back
              onClick {
                  ui.owner.finish()
              }
          }.lparams(width = matchParent, height = wrapContent)
      }
    }

}