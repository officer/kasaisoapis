package com.kasaiso.com.kasaisoapis.controllers


import com.kasaiso.com.kasaisoapis.constants.KasaisoApiConstants
import com.kasaiso.com.kasaisoapis.exceptions.DatetimeParseException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.bind.DefaultValue
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@RestController
class DateTimeController {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DateTimeController::class.java)
        private val datetimeParsers: List<DateFormat> = KasaisoApiConstants.AcceptedDatetimeFormats.map { SimpleDateFormat(it) }
        
    }

    @RequestMapping(
        path = ["/datetimes"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getAcceptedDatetimeFormats(): List<String> {
        return KasaisoApiConstants.AcceptedDatetimeFormats
    }

    @RequestMapping(
        path = ["/convert_datetime"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun convertFromDateTimeString(
        @RequestParam("datetime") datetime: String,
        @DefaultValue("UTC") @RequestParam("from_timezone") from_timezone: String,
        @DefaultValue("UTC") @RequestParam("to_timezone") to_timezone: String
    ): Map<String, String?> {
        logger.debug("Received datetime %s, timezone %s", datetime, from_timezone)
        return try {
            val timestamp: Long?
            val date: String?
            for (datetimeFormatter in datetimeParsers){
                datetimeFormatter.timeZone = TimeZone.getTimeZone(from_timezone)
                timestamp = datetimeFormatter.parse(datetime).time
                datetimeFormatter.timeZone = TimeZone.getTimeZone(to_timezone)
                date = datetimeFormatter.format(timestamp)
                return mapOf(
                    "timestamp" to String.format("%d", timestamp),
                    "date" to date
                )
            }
            throw DatetimeParseException("Unable to parse datetime")
        } catch (e: ParseException) {
            e.stackTrace.iterator().forEach {
                logger.error(it.toString())
            }
            throw DatetimeParseException(e.message!!)
        } catch (e: Exception) {
            e.printStackTrace()
            throw DatetimeParseException(e.message!!)
        }
    }

    @RequestMapping(
        path = ["/convert_timestamp"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun convertFromTimestamp(
        @RequestParam("timestamp") timestamp: Long,
        @DefaultValue("UTC") @RequestParam("timezone") timezone: String
    ): Map<String, String?> {
        return try {
            val datetimeFormatter: DateFormat = datetimeParsers[0]
            datetimeFormatter.timeZone = TimeZone.getTimeZone(timezone)
            return mapOf(
                "timestamp" to String.format("%d", timestamp),
                "date" to datetimeFormatter.format(timestamp)
            )
        } catch (e: Exception){
            e.printStackTrace()
            throw DatetimeParseException(e.message!!)
        }
    }
}