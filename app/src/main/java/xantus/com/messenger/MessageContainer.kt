package xantus.com.messenger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by dravk on 8/31/2017.
 */
@Parcelize
data class MessageContainer(val from: String = "", val to: String = "", val subject: String = "", val body: String = "") : Parcelable