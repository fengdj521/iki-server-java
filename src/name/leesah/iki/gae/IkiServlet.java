package name.leesah.iki.gae;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class IkiServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // uid & info, cannot be null or empty
        String uid = req.getParameter("u");
        String info = req.getParameter("i");
        // latitude & longitude, must be numbers
        String latitudeString = req.getParameter("a");
        String longitudeString = req.getParameter("o");

        resp.setContentType("text/plain");
        resp.getWriter().println(
                "'" + uid + "' reported '" + info + "' from '" + latitudeString
                        + "/" + longitudeString + "'.");

        if (uid != null && !uid.isEmpty() && info != null && !info.isEmpty()
                && latitudeString.matches("[-+]?\\d*\\.?\\d+")
                && longitudeString.matches("[-+]?\\d*\\.?\\d+")) {

            try {
                Iki.know(uid, info, Long.parseLong(latitudeString),
                        Long.parseLong(longitudeString));
            } catch (IkiException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
