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

        assertFalse(Iki.isEmailValid(""));
        assertFalse(Iki.isEmailValid("user"));
        assertFalse(Iki.isEmailValid("user@"));
        assertFalse(Iki.isEmailValid("example.com"));
        assertFalse(Iki.isEmailValid("@example.com"));
        assertFalse(Iki.isEmailValid("user.example.com"));
        assertFalse(Iki.isEmailValid("user@example"));
        assertFalse(Iki.isEmailValid("us@er@example.com"));
        assertFalse(Iki.isEmailValid("@example"));
        assertFalse(Iki.isEmailValid("user~@example.com"));
        assertFalse(Iki.isEmailValid("user@example#.com"));
        assertFalse(Iki.isEmailValid("user@example~.com"));
        assertFalse(Iki.isEmailValid("user@example.com1"));
        assertFalse(Iki.isEmailValid("user@example.com#"));
        assertFalse(Iki.isEmailValid("user@example.com-"));
        assertFalse(Iki.isEmailValid("user@example.com_"));
        assertFalse(Iki.isEmailValid("user@example.com~"));

        assertTrue(Iki.isEmailValid("user@example.com"));
        assertTrue(Iki.isEmailValid("1user@example.com"));
        assertTrue(Iki.isEmailValid("user1@example.com"));
        assertTrue(Iki.isEmailValid("user#@example.com"));
        assertTrue(Iki.isEmailValid("user-@example.com"));
        assertTrue(Iki.isEmailValid("user_@example.com"));
        assertTrue(Iki.isEmailValid("user.@example.com"));
        assertTrue(Iki.isEmailValid("user@1example.com"));
        assertTrue(Iki.isEmailValid("user@example1.com"));
        assertTrue(Iki.isEmailValid("user@example-.com"));
        assertTrue(Iki.isEmailValid("user@example_.com"));
        assertTrue(Iki.isEmailValid("user@leesah.name"));
    }
}
