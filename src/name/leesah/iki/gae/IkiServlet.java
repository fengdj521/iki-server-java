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
        String u = req.getParameter("u");
        String i = req.getParameter("i");
        long a = Long.parseLong(req.getParameter("a"));
        long o = Long.parseLong(req.getParameter("o"));

        u = u == null || u.isEmpty() ? "Anonymous user" : u;

        String string;
        try {
            Iki.know(u, i, a, o);
            if (i == null || i.isEmpty()) {
                string = "Error!";
            } else {
                string = u + " reported " + i + " from " + a + "/" + o + ".";
            }
        } catch (IkiException e) {
            string = e.getMessage();
        }
        resp.setContentType("text/plain");
        resp.getWriter().println(string);

    }
}
