package xantus.com.messenger

import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

/**
 * Created by dravk on 8/31/2017.
 */
fun calculateDeliveryTime(delay: Int) : String{
    val referencetimeZone : ZoneId = ZoneId.of(ZoneOffset.getAvailableZoneIds().elementAt(0))
    val timeNowAtReferenceTimeZone = ZonedDateTime.now(referencetimeZone)
    val deliveryTime = timeNowAtReferenceTimeZone.plusHours(delay.toLong())
    return deliveryTime.toString()
}

fun isAvailable(deliveryTimeAsString: String) : Boolean{
    val referencetimeZone : ZoneId = ZoneId.of(ZoneOffset.getAvailableZoneIds().elementAt(0))
    val timeNowAtReferenceTimeZone = ZonedDateTime.now(referencetimeZone)
    val deliveryTime = ZonedDateTime.parse(deliveryTimeAsString)
    return timeNowAtReferenceTimeZone.isAfter(deliveryTime)
}

fun calculateDeliveryDelay(coordsFrom: Coords, coordsTo: Coords) : Int {
    val speed = 1000
    if(coordsFrom.lat != null &&
            coordsFrom.lng != null &&
            coordsTo.lat != null &&
            coordsTo.lng != null) {
        val earthRadiusKm = 6371
        val dLat = degreesToRadians(coordsTo.lat - coordsFrom.lat)
        val dLng = degreesToRadians(coordsTo.lng - coordsFrom.lng)

        val lat1 = degreesToRadians(coordsFrom.lat)
        val lat2 = degreesToRadians(coordsTo.lat)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLng / 2) * Math.sin(dLng / 2) * Math.cos(lat1) * Math.cos(lat2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val tmp =  earthRadiusKm * c/speed
        return tmp.toInt()
    } else{
        return 0
    }
}



fun degreesToRadians(degrees : Double) : Double{
    return degrees * Math.PI/180
}
