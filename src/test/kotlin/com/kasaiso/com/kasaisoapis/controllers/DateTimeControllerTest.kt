package com.kasaiso.com.kasaisoapis.controllers

import com.kasaiso.com.kasaisoapis.config.TestConfig
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.test.context.event.annotation.BeforeTestClass
import java.net.URI
import java.util.*

@SpringBootTest
@Import(TestConfig::class)
class DateTimeControllerTest(
) {

    private lateinit var calender: Calendar

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    private lateinit var dateTimeController: DateTimeController

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DateTimeControllerTest::class.java)
    }

    @BeforeTestClass
    fun before() {
        this.calender = Calendar.getInstance()
    }

    @Test
    fun testValidDatetimeAndTimezoneShouldReturnValidResultUTC2UTC() {
        val expect: Map<String, String> = mapOf(
            "error" to "",
            "timestamp" to "1669898712000",
            "date" to "2022-12-01T12:45:12"
        )
        val actual: Map<String, String?> = testRestTemplate.getForObject(URI("http://localhost:8080/convert_datetime?datetime=2022-12-01T12:45:12&from_timezone=UTC&to_timezone=UTC"), Map::class.java) as Map<String, String?>
        assert((
            actual["timestamp"] == expect["timestamp"]
                    &&
            actual["date"] == expect["date"]
        ))
    }

    @Test
    fun testValidDatetimeAndTimezoneShouldReturnValidResultUTC2JST() {
        val expect: Map<String, String> = mapOf(
            "error" to "",
            "timestamp" to "1669898712000",
            "date" to "2022-12-01T21:45:12"
        )
        val actual: Map<String, String?> = testRestTemplate.getForObject(URI("http://localhost:8080/convert_datetime?datetime=2022-12-01T12:45:12&from_timezone=UTC&to_timezone=JST"), Map::class.java) as Map<String, String?>
        actual.entries.forEach {
            logger.error(it.key + ":" + it.value)
        }
        assert((
            actual["timestamp"] == expect["timestamp"]
                    &&
            actual["date"] == expect["date"]
        ))
    }

    @Test
    fun testInvalidDatetimeShouldReturnError() {
        val expect: Map<String, String> = mapOf(
            "error" to "Unable to parse the date",
            "timestamp" to "",
            "date" to ""
        )
        val datetime: String = "2022-12-01TZ120:45:12"
        val from_timezone = "UTC"
        val to_timezone: String = "JST"
        val uri: URI = URI(String.format("http://localhost:8080/convert_datetime?datetime=%s&from_timezone=%s&to_timezone=%s", datetime, from_timezone, to_timezone))
        val actual: Map<String, String?> = testRestTemplate.getForObject(uri, Map::class.java) as Map<String, String?>
        actual.entries.forEach {
            logger.error(it.key + " : " + it.value)
        }
        assert((
            actual["timestamp"] == expect["timestamp"]
                    &&
            actual["date"] == expect["date"]
        ))
    }


    @Test
    fun testValidTimestampShouldReturnValidResult() {

    }
}