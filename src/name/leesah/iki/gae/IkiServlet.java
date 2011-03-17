package name.leesah.iki.gae;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class IkiServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String uid = req.getParameter("u");
        String info = req.getParameter("i");
        String latitude = req.getParameter("a");
        String longitude = req.getParameter("o");

        resp.setContentType("text/plain");
        resp.getWriter().println("'" + uid + "' reported '" + info + "' from '" + latitude + "/" + longitude + "'.");

        // uid & info are not empty. latitude & longitude are numbers
        if (uid != null && info != null && latitude != null && longitude != null && !uid.isEmpty() && !info.isEmpty()
                && latitude.matches("[-+]?\\d*\\.?\\d+") && longitude.matches("[-+]?\\d*\\.?\\d+")) {

            try {
                Iki.know(uid, info, Long.parseLong(latitude), Long.parseLong(longitude));
            } catch (IkiException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
