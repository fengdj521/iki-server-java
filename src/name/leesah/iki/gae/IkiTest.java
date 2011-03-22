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
public class IkiTest {

    /**
     * Test method for
     * {@link name.leesah.iki.gae.UidIki#isEmailValid(java.lang.String)} .
     */
    @Test
    public void testIsEmailValid() {

        assertFalse(Iki.getInstance().isEmailValid(""));
        assertFalse(Iki.getInstance().isEmailValid("user"));
        assertFalse(Iki.getInstance().isEmailValid("user@"));
        assertFalse(Iki.getInstance().isEmailValid("example.com"));
        assertFalse(Iki.getInstance().isEmailValid("@example.com"));
        assertFalse(Iki.getInstance().isEmailValid("user.example.com"));
        assertFalse(Iki.getInstance().isEmailValid("user@example"));
        assertFalse(Iki.getInstance().isEmailValid("us@er@example.com"));
        assertFalse(Iki.getInstance().isEmailValid("@example"));
        assertFalse(Iki.getInstance().isEmailValid("user~@example.com"));
        assertFalse(Iki.getInstance().isEmailValid("user@example#.com"));
        assertFalse(Iki.getInstance().isEmailValid("user@example~.com"));
        assertFalse(Iki.getInstance().isEmailValid("user@example.com1"));
        assertFalse(Iki.getInstance().isEmailValid("user@example.com#"));
        assertFalse(Iki.getInstance().isEmailValid("user@example.com-"));
        assertFalse(Iki.getInstance().isEmailValid("user@example.com_"));
        assertFalse(Iki.getInstance().isEmailValid("user@example.com~"));

        assertTrue(Iki.getInstance().isEmailValid("user@example.com"));
        assertTrue(Iki.getInstance().isEmailValid("1user@example.com"));
        assertTrue(Iki.getInstance().isEmailValid("user1@example.com"));
        assertTrue(Iki.getInstance().isEmailValid("user#@example.com"));
        assertTrue(Iki.getInstance().isEmailValid("user-@example.com"));
        assertTrue(Iki.getInstance().isEmailValid("user_@example.com"));
        assertTrue(Iki.getInstance().isEmailValid("user.@example.com"));
        assertTrue(Iki.getInstance().isEmailValid("user@1example.com"));
        assertTrue(Iki.getInstance().isEmailValid("user@example1.com"));
        assertTrue(Iki.getInstance().isEmailValid("user@example-.com"));
        assertTrue(Iki.getInstance().isEmailValid("user@example_.com"));
        assertTrue(Iki.getInstance().isEmailValid("user@leesah.name"));
        assertTrue(Iki.getInstance().isEmailValid("test@leesah.name"));
    }
}
