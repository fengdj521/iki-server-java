/**
 * 
 */
package name.leesah.iki.gae;

import java.util.Arrays;

import javax.persistence.EntityExistsException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * @author esaalii
 * 
 */
public class Iki {

    private static final DatastoreService datastore = DatastoreServiceFactory
            .getDatastoreService();

    public static void know(String u, String i, long a, long o)
            throws IkiException {
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
        user.setProperty("latestLongtitude", o);

        Entity report = new Entity("Report");
        report.setProperty("u", u);
        report.setProperty("i", i);
        report.setProperty("a", a);
        report.setProperty("o", o);

        datastore.put(Arrays.asList(user, report));
    }

    public static String signUp(String email) throws IkiException {
        if (email == null) {
            throw new IkiException("Invalid email.");
        }

        Query q = new Query("User");
        q.addFilter("email", Query.FilterOperator.EQUAL, email);
        PreparedQuery pq = datastore.prepare(q);

        if (0 != pq.countEntities(FetchOptions.Builder.withLimit(1))) {
            throw new EntityExistsException("User exists: " + email);
        }

        Entity user = new Entity("User");
        user.setProperty("email", email);
        user.setProperty("credits", 0);
        user.setProperty("fines", 0);
        user.setProperty("pains", 0);

        Key key = datastore.put(user);
        return KeyFactory.keyToString(key);

    }
}
