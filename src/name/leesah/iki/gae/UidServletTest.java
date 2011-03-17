/**
 * 
 */
package name.leesah.iki.gae;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Sah
 * 
 */
public class UidServletTest {

    /**
     * Test method for
     * {@link name.leesah.iki.gae.UidServlet#isEmailValid(java.lang.String)} .
     */
    @Test
    public void testIsEmailValid() {
        UidServlet servlet = new UidServlet();

        assertFalse(servlet.isEmailValid(""));
        assertFalse(servlet.isEmailValid("user"));
        assertFalse(servlet.isEmailValid("user@"));
        assertFalse(servlet.isEmailValid("example.com"));
        assertFalse(servlet.isEmailValid("@example.com"));
        assertFalse(servlet.isEmailValid("user.example.com"));
        assertFalse(servlet.isEmailValid("user@example"));
        assertFalse(servlet.isEmailValid("us@er@example.com"));
        assertFalse(servlet.isEmailValid("@example"));
        assertFalse(servlet.isEmailValid("user~@example.com"));
        assertFalse(servlet.isEmailValid("user@example#.com"));
        assertFalse(servlet.isEmailValid("user@example~.com"));
        assertFalse(servlet.isEmailValid("user@example.com1"));
        assertFalse(servlet.isEmailValid("user@example.com#"));
        assertFalse(servlet.isEmailValid("user@example.com-"));
        assertFalse(servlet.isEmailValid("user@example.com_"));
        assertFalse(servlet.isEmailValid("user@example.com~"));

        assertTrue(servlet.isEmailValid("user@example.com"));
        assertTrue(servlet.isEmailValid("1user@example.com"));
        assertTrue(servlet.isEmailValid("user1@example.com"));
        assertTrue(servlet.isEmailValid("user#@example.com"));
        assertTrue(servlet.isEmailValid("user-@example.com"));
        assertTrue(servlet.isEmailValid("user_@example.com"));
        assertTrue(servlet.isEmailValid("user.@example.com"));
        assertTrue(servlet.isEmailValid("user@1example.com"));
        assertTrue(servlet.isEmailValid("user@example1.com"));
        assertTrue(servlet.isEmailValid("user@example-.com"));
        assertTrue(servlet.isEmailValid("user@example_.com"));
        assertTrue(servlet.isEmailValid("user@leesah.name"));
    }
}
