package name.leesah.iki.gae;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SignUpServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String string = req.getParameter("email");
        try {
            string = Iki.signUp(string);
        } catch (IkiException e) {
            string = e.getMessage();
        }

        resp.setContentType("text/plain");
        resp.getWriter().println(string);

    }
}
