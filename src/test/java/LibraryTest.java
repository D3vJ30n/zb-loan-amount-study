import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.DriverManager;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private static class TestData {
        private String message;

        public TestData() {
            this.message = "test";
        }

        public String getMessage() {
            return message;
        }
    }

    @Test
    void jacksonTest() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TestData data = new TestData();
            String json = mapper.writeValueAsString(data);
            assertEquals("{\"message\":\"test\"}", json);
        } catch (Exception e) {
            fail("Jackson Fail: " + e.getMessage());
        }
    }

    @Test
    void mysqlTest() {
        try {
            // MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.219.101:3306/zbproject01?useSSL=false&serverTimezone=Asia/Seoul",
                "root",
                "6188"
            );
            assertNotNull(conn, "Connection should not be null");
            conn.close();
        } catch (Exception e) {
            fail("MySQL Connection Fail: " + e.getMessage());
        }
    }


    @Test
    void lombokTest() {
        TestData data = new TestData();
        assertEquals("test", data.getMessage());
    }
}