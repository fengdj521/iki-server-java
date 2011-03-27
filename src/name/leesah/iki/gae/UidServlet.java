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
public class UidServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter(Constants.E);

        resp.setContentType("text/plain");
        resp.getWriter().println("e = " + email);

        try {

            String uid = Iki.getInstance().uid(email);
            resp.setHeader(Constants.UID, uid);
            resp.getWriter().println("uid = " + uid);

        } catch (IkiException e) {
            resp.setHeader(Constants.IKI_ERROR, e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            resp.getWriter().println(e.getMessage());
        }

    }

}
