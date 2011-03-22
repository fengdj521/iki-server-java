/**
 * 
 */
package name.leesah.iki.gae;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

/**
 * @author Sah
 * 
 */
public class Iki {

    private static final String INFO_P = "p";
    private static final String INFO_F = "f";

    private static final double WEIGHT_INCREMENT = 1.05;
    private static final double WEIGHT_DECREMENT = 0.5;

    private static final long MILLIS_OF_A_DAY = 1000 * 60 * 60 * 24;
    private static final long MILLIS_OF_A_WEEK = MILLIS_OF_A_DAY * 7;
    private static final long MILLIS_OF_A_MONTH = MILLIS_OF_A_DAY * 30;

    private static final String ENTITY_TYPE_USER = "User";
    private static final String PROP_USER_EMAIL = "email";
    private static final String PROP_USER_CREDITS = "credits";
    private static final String PROP_USER_PAINS = "pains";
    private static final String PROP_USER_FINES = "fines";
    private static final String PROP_USER_LATEST_LATITUDE = "latest_latitude";
    private static final String PROP_USER_LATEST_LONGITUDE = "latest_longitude";
    private static final String PROP_USER_LATEST_TIME = "latestTime";

    private static final String ENTITY_TYPE_KNOWLEDGE = "Knowledge";
    private static final String PROP_KNOWLEDGE_UID = "uid";
    private static final String PROP_KNOWLEDGE_INFO = "info";
    private static final String PROP_KNOWLEDGE_LATITUDE = "latitude";
    private static final String PROP_KNOWLEDGE_LONGITUDE = "longitude";
    private static final String PROP_KNOWLEDGE_TIME = "time";

    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static Iki instance;

    public static Iki getInstance() {
        if (null == instance) {
            instance = new Iki();
        }
        return instance;

    }

    /**
     * @param email
     * @return a unique string value for the user
     * @throws IkiException
     */
    public String uid(String email) throws IkiException {

        // u kidding me?
        if (!isEmailValid(email)) {
            throw new IkiException("Invalid email address: " + email);
        }

        Key key;

        // query
        List<Entity> entityList = datastore.prepare(
                new Query(ENTITY_TYPE_USER).addFilter(PROP_USER_EMAIL, Query.FilterOperator.EQUAL, email)).asList(
                FetchOptions.Builder.withLimit(2));

        // new user
        if (0 == entityList.size()) {
            Entity user = new Entity(ENTITY_TYPE_USER);
            user.setProperty(PROP_USER_EMAIL, email);
            user.setProperty(PROP_USER_CREDITS, 0);
            user.setProperty(PROP_USER_FINES, 0);
            user.setProperty(PROP_USER_PAINS, 0);
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
     * @param uid
     * @param info
     * @param latitude
     * @param longitude
     * @throws IkiException
     * @throws EntityNotFoundException
     */
    public void know(String uid, String info, long latitude, long longitude) throws IkiException, EntityNotFoundException {

        Entity user = datastore.get(KeyFactory.stringToKey(uid));

        // update user
        user.setProperty(PROP_USER_CREDITS, ((Integer) user.getProperty(PROP_USER_CREDITS)) + 1);
        if (INFO_F.equals(info)) {
            user.setProperty(PROP_USER_FINES, ((Integer) user.getProperty(PROP_USER_FINES)) + 1);
        } else if (INFO_P.equals(info)) {
            user.setProperty(PROP_USER_PAINS, ((Integer) user.getProperty(PROP_USER_PAINS)) + 1);
        } else {
            throw new IkiException("Unknown info: " + info + ".");
        }
        user.setProperty(PROP_USER_LATEST_LATITUDE, latitude);
        user.setProperty(PROP_USER_LATEST_LONGITUDE, longitude);
        user.setProperty(PROP_USER_LATEST_TIME, longitude);

        // knowing
        Entity knowledge = new Entity(ENTITY_TYPE_KNOWLEDGE);
        knowledge.setProperty(PROP_KNOWLEDGE_UID, uid);
        knowledge.setProperty(PROP_KNOWLEDGE_INFO, info);
        knowledge.setProperty(PROP_KNOWLEDGE_LATITUDE, latitude);
        knowledge.setProperty(PROP_KNOWLEDGE_LONGITUDE, longitude);
        knowledge.setProperty(PROP_KNOWLEDGE_TIME, System.currentTimeMillis());

        datastore.put(Arrays.asList(user, knowledge));

    }

    /**
     * @param latitude
     * @param longitude
     * @return
     * @throws EntityNotFoundException
     */
    public double predict(long latitude, long longitude) throws EntityNotFoundException {

        // all pain knowledges
        Query queryPains = new Query(ENTITY_TYPE_KNOWLEDGE);
        queryPains.addFilter(PROP_KNOWLEDGE_INFO, Query.FilterOperator.EQUAL, INFO_P);
        queryPains.addFilter(PROP_KNOWLEDGE_LATITUDE, Query.FilterOperator.GREATER_THAN, latitude - 1);
        queryPains.addFilter(PROP_KNOWLEDGE_LATITUDE, Query.FilterOperator.LESS_THAN, latitude + 1);
        queryPains.addFilter(PROP_KNOWLEDGE_LONGITUDE, Query.FilterOperator.GREATER_THAN, longitude - 1);
        queryPains.addFilter(PROP_KNOWLEDGE_LONGITUDE, Query.FilterOperator.GREATER_THAN, longitude + 1);
        queryPains.addFilter(PROP_KNOWLEDGE_TIME, Query.FilterOperator.GREATER_THAN, System.currentTimeMillis()
                - MILLIS_OF_A_WEEK);

        List<Entity> pains = datastore.prepare(queryPains).asList(FetchOptions.Builder.withLimit(2));
        // all fines knowledges
        Query queryFines = new Query(ENTITY_TYPE_KNOWLEDGE);
        queryFines.addFilter(PROP_KNOWLEDGE_INFO, Query.FilterOperator.EQUAL, INFO_P);
        queryFines.addFilter(PROP_KNOWLEDGE_LATITUDE, Query.FilterOperator.GREATER_THAN, latitude - 1);
        queryFines.addFilter(PROP_KNOWLEDGE_LATITUDE, Query.FilterOperator.LESS_THAN, latitude + 1);
        queryFines.addFilter(PROP_KNOWLEDGE_LONGITUDE, Query.FilterOperator.GREATER_THAN, longitude - 1);
        queryFines.addFilter(PROP_KNOWLEDGE_LONGITUDE, Query.FilterOperator.GREATER_THAN, longitude + 1);
        queryPains.addFilter(PROP_KNOWLEDGE_TIME, Query.FilterOperator.GREATER_THAN, System.currentTimeMillis()
                - MILLIS_OF_A_WEEK);
        List<Entity> fines = datastore.prepare(queryPains).asList(FetchOptions.Builder.withLimit(2));

        // all users in this region
        Query queryUsers = new Query(ENTITY_TYPE_USER);
        queryUsers.addFilter(PROP_USER_LATEST_LATITUDE, Query.FilterOperator.GREATER_THAN, latitude - 1);
        queryUsers.addFilter(PROP_USER_LATEST_LATITUDE, Query.FilterOperator.LESS_THAN, latitude + 1);
        queryUsers.addFilter(PROP_USER_LATEST_LONGITUDE, Query.FilterOperator.GREATER_THAN, longitude - 1);
        queryUsers.addFilter(PROP_USER_LATEST_LONGITUDE, Query.FilterOperator.GREATER_THAN, longitude + 1);
        queryUsers.addFilter(PROP_USER_LATEST_TIME, Query.FilterOperator.GREATER_THAN, System.currentTimeMillis()
                - MILLIS_OF_A_MONTH);
        List<Entity> users = datastore.prepare(queryPains).asList(FetchOptions.Builder.withLimit(2));

        // processed data for pain knowledges
        Map<String, Double> painsMap = new HashMap<String, Double>();
        for (Entity knowledge : pains) {
            String uid = (String) knowledge.getProperty(PROP_KNOWLEDGE_UID);
            if (painsMap.containsKey(uid)) {
                painsMap.put(uid, painsMap.get(uid) * WEIGHT_INCREMENT);
            } else {
                painsMap.put(uid,
                        ((Integer) datastore.get(KeyFactory.stringToKey(uid)).getProperty(PROP_USER_CREDITS)).doubleValue());
            }
        }

        // processed data for fine knowledges
        Map<String, Double> finesMap = new HashMap<String, Double>();
        for (Entity knowledge : fines) {
            String uid = (String) knowledge.getProperty(PROP_KNOWLEDGE_UID);
            if (finesMap.containsKey(uid)) {
                finesMap.put(uid, finesMap.get(uid) * WEIGHT_INCREMENT);
            } else {
                finesMap.put(uid,
                        ((Integer) datastore.get(KeyFactory.stringToKey(uid)).getProperty(PROP_USER_CREDITS)).doubleValue());
            }
        }

        // processed data for users not reporting recently
        Map<String, Double> unknownsMap = new HashMap<String, Double>();
        for (Entity user : users) {
            String uid = KeyFactory.keyToString(user.getKey());
            if (!painsMap.containsKey(uid) && !finesMap.containsKey(uid)) {
                unknownsMap.put(uid, (Double) user.getProperty(PROP_USER_CREDITS));
            }
        }

        // weighted value of wet
        double wet = 0;
        for (Double weighted : painsMap.values()) {
            wet += weighted;
        }

        // weighted value of dry
        double dry = 0;
        for (Double weighted : painsMap.values()) {
            dry += weighted;
        }

        // weighted value of dry
        double unknown = 0;
        for (Double weighted : painsMap.values()) {
            unknown += weighted * WEIGHT_DECREMENT;
        }

        // possibility of raining
        return wet / (wet + dry + unknown);

    }

    /**
     * @param email
     * @return
     */
    boolean isEmailValid(String email) {
        return email.matches("[A-Za-z0-9\\.\\-_#]+\\@[A-Za-z0-9\\._-]+\\.[A-Za-z]+");
    }
}
