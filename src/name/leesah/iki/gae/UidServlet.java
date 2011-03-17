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

        String email = req.getParameter("e");

        resp.setContentType("text/plain");
        resp.getWriter().println("e = " + email);

        if (email != null && !email.isEmpty() && isEmailValid(email)) {

            try {
                String uid = Iki.getUid(email);
                resp.setHeader("uid", uid);
                resp.getWriter().println("uid = " + uid);

            } catch (IkiException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().println(e.getMessage());
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(":-(");
        }

    }

    /**
     * @param email
     * @return
     */
    protected boolean isEmailValid(String email) {
        return email.matches("[A-Za-z0-9\\.\\-_#]+\\@[A-Za-z0-9\\._-]+\\.[A-Za-z]+");
    }
}
