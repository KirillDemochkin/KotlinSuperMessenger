package xantus.com.messenger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by dravk on 8/24/2017.
 */
@Parcelize
data class Coords(val lat : Double? = 0.0, val lng : Double? = 0.0) : Parcelable