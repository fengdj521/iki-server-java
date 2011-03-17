/**
 * 
 */
package name.leesah.iki.gae;

import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

/**
 * @author esaalii
 * 
 */
public class Iki {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    /**
     * @param email
     * @return
     * @throws IkiException
     */
    public static String getUid(String email) throws IkiException {

        // u kidding me?
        if (email == null || email.isEmpty()) {
            throw new IkiException("Invalid email address.");
        }

        Key key;

        // query
        List<Entity> entityList = datastore.prepare(new Query("User").addFilter("email", Query.FilterOperator.EQUAL, email))
                .asList(FetchOptions.Builder.withLimit(2));

        // new user
        if (0 == entityList.size()) {
            Entity user = new Entity("User");
            user.setProperty("email", email);
            user.setProperty("credits", 0);
            user.setProperty("fines", 0);
            user.setProperty("pains", 0);
            key = datastore.put(user);
        }

        // existing user
        else if (1 == entityList.size()) {
            key = entityList.get(0).getKey();

        }

        // crap!
        else {
            throw new IkiException("More than one user matches email: '" + email + "'.");
        }

        return KeyFactory.keyToString(key);

    }

    /**
     * @param u
     *            <b>u</b>id
     * @param i
     *            <b>i</b>nfo, f for fine, p for pain
     * @param a
     *            l<b>a</b>titude
     * @param o
     *            l<b>o</b>ngitude
     * @throws IkiException
     */
    public static void know(String u, String i, long a, long o) throws IkiException {

        // anonymous users are banned.
        Entity user;
        try {
            user = datastore.get(KeyFactory.stringToKey(u));
        } catch (EntityNotFoundException e) {
            throw new IkiException(e);
        }

        user.setProperty("credits", ((Integer) user.getProperty("credits")) + 1);
        if ("fine".equals(i)) {
            user.setProperty("fines", ((Integer) user.getProperty("fines")) + 1);
        } else {
            user.setProperty("pains", ((Integer) user.getProperty("pains")) + 1);
        }
        user.setProperty("latestLatitude", a);
        user.setProperty("latestLongitude", o);

        Entity report = new Entity("Report");
        report.setProperty("u", u);
        report.setProperty("i", i);
        report.setProperty("a", a);
        report.setProperty("o", o);

        datastore.put(Arrays.asList(user, report));
    }
}
