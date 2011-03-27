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

        String uid = req.getParameter(Constants.U);
        String info = req.getParameter(Constants.I);
        String latitude = req.getParameter(Constants.A);
        String longitude = req.getParameter(Constants.O);

        resp.setContentType("text/plain");
        resp.getWriter().println("'" + uid + "' reported '" + info + "' from '" + latitude + "/" + longitude + "'.");

        try {

            Iki.getInstance().know(uid, info, Double.parseDouble(latitude), Double.parseDouble(longitude));

        } catch (IkiException e) {
            resp.setHeader(Constants.IKI_ERROR, e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e) {
            resp.setHeader(Constants.IKI_ERROR, e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (EntityNotFoundException e) {
            resp.setHeader(Constants.IKI_ERROR, e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
