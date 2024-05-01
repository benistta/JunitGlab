import static org.junit.jupiter.api.Assertions.*;

import org.example.PasswordComply;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class PasswordComplyTest {

    @Test
    void testDoesPasswordComply() {
        PasswordComply password = new PasswordComply("Test1234");
        assertTrue(password.doesPasswordComply(), "Password does not meet the conditions.");
    }

    @Test
    void testDoesPasswordExist() {
        PasswordComply password = new PasswordComply("Test1234!");
        assertThrows(SQLException.class, password::doesNotAlreadyExist, "Expected SQLException was not thrown.");
    }

    @Test
    void testEmptyPassword() {
        PasswordComply password = new PasswordComply("");
        assertThrows(IllegalArgumentException.class, password::verifyPasswordLength, "Expected IllegalArgumentException was not thrown for empty password.");
    }
}
