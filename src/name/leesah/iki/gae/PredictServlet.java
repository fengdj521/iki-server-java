/**
 * 
 */
package name.leesah.iki.gae;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sah
 * 
 */
@SuppressWarnings("serial")
public class PredictServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String latitude = req.getParameter("a");
        String longitude = req.getParameter("o");

        resp.setContentType("text/plain");
        resp.getWriter().println("predicton on '" + latitude + "/" + longitude + "'.");

        // latitude & longitude are numbers
        if (latitude != null && longitude != null && latitude.matches("[-+]?\\d*\\.?\\d+")
                && longitude.matches("[-+]?\\d*\\.?\\d+")) {

            Iki.predict(Long.parseLong(latitude), Long.parseLong(longitude));

        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
