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
        String uid = req.getParameter("u");
        String info = req.getParameter("i");
        long latitude = Long.parseLong(req.getParameter("a"));
        long longtitude = Long.parseLong(req.getParameter("o"));

        uid = uid == null || uid.isEmpty() ? "Anonymous user" : uid;

        String text;
        try {
            Iki.know(uid, info, latitude, longtitude);
            if (info == null || info.isEmpty()) {
                text = "Error!";
            } else {
                text = uid + " reported " + info + " from " + latitude + "/" + longtitude + ".";
            }
        } catch (IkiException e) {
            text = e.getMessage();
        }
        resp.setContentType("text/plain");
        resp.getWriter().println(text);

    }
}
