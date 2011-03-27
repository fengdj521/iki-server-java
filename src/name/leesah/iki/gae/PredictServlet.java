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

        String latitude = req.getParameter(Constants.A);
        String longitude = req.getParameter(Constants.O);

        resp.setContentType("text/plain");
        resp.getWriter().println("predicton on '" + latitude + "/" + longitude + "'.");

        try {

            String prediction = String.valueOf(Iki.getInstance().predict(Double.parseDouble(latitude),
                    Double.parseDouble(longitude)));
            resp.setHeader(Constants.PREDICTION, prediction);
            resp.getWriter().println("prediction = " + prediction);

        } catch (NumberFormatException e) {
            resp.setHeader(Constants.IKI_ERROR, e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (EntityNotFoundException e) {
            resp.setHeader(Constants.IKI_ERROR, e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }

    }

}
