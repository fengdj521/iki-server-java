/**
 * 
 */
package name.leesah.iki.gae;

/**
 * @author esaalii
 * 
 */
public class IkiException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8764735787212730056L;

    public IkiException(String message) {
        super(message);
    }

    public IkiException(Throwable cause) {
        super(cause);
    }

}
