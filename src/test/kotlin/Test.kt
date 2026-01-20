import com.kjson.JArray
import com.kjson.JObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Test {
    @Test
    fun doTest() {
        val data = """
            ["ehu", 1, 2.0, {"obj": "value"}]
        """.trimIndent()
        val array = JArray.parse(data)
        Assertions.assertEquals(array.extractString("3.obj"), "value")
    }

    @Test
    fun doTest2() {
        val data = """
            {"status":"success","data":{"activities_summary_stats":[{"activity_id":"63472719","status":"4","widget_id":"8895","type":"65","points_reason":"Product Purchase (single invoice)","numeric_value":"1","activity_date_in_UTC":"2026-01-20 12:27:28","transaction_time":"2026-01-20 12:27:28","passed_activity_id":"1768912047","activity_log_date":"2026-01-20 12:27:27","points_earned":0,"timestamp":1768912048,"attributes":{"imagePath":"https:\/\/s3.amazonaws.com\/userfiles.nextbee.com\/hasa.nextbeeapp.com\/receipt\/image_picker_E8748CBE-0C0F-4639-837C-3FF368348870-16430-000009961154761D.jpg","admin_approved":"0","is_statement":"false"},"activity_products":null}],"total_records":1}}
        """.trimIndent()
        val json = JObject.parse(data)
        val array = json.tryExtractList("data.activities_summary_stats")?.toList()
        Assertions.assertTrue {
            array != null
        }
    }
}