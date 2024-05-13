import com.kjson.JArray
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
}