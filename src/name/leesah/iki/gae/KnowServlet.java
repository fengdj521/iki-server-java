package name.leesah.iki.gae;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * @author Sah
 * 
 */
@SuppressWarnings("serial")
public class KnowServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String uid = req.getParameter("u");
        String info = req.getParameter("i");
        String latitude = req.getParameter("a");
        String longitude = req.getParameter("o");

        resp.setContentType("text/plain");
        resp.getWriter().println("'" + uid + "' reported '" + info + "' from '" + latitude + "/" + longitude + "'.");

        try {
            Iki.know(uid, info, Long.parseLong(latitude), Long.parseLong(longitude));
        } catch (IkiException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
