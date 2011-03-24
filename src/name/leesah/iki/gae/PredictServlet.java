/**
 * 
 */
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
public class PredictServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String latitude = req.getParameter("a");
        String longitude = req.getParameter("o");

        resp.setContentType("text/plain");
        resp.getWriter().println("predicton on '" + latitude + "/" + longitude + "'.");

        try {

            String prediction = String.valueOf(Iki.getInstance().predict(Double.parseDouble(latitude),
                    Double.parseDouble(longitude)));
            resp.setHeader("prediction", prediction);
            resp.getWriter().println("prediction = " + prediction);

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

}
