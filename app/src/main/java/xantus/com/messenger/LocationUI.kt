package xantus.com.messenger

import android.view.View
import android.widget.EditText
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by dravk on 8/24/2017.
 */
class LocationUI : AnkoComponent<LocationActivity>{
    lateinit var userLocation : EditText
    override fun createView(ui: AnkoContext<LocationActivity>): View = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)

            userLocation = editText {
                id = R.id.userLocationEditText
                hintResource = R.string.location_hint
                textSize = 24f
            }.lparams(width = matchParent, height = wrapContent)

            button {
                id = R.id.userLocationButton
                textResource = R.string.location_button
                onClick {
                    if(!userLocation.text.isBlank()){
                        ui.owner.locateUser(userLocation.text.toString().trim())

                    }
                }
            }.lparams(width = matchParent, height = wrapContent)
        }
    }

}