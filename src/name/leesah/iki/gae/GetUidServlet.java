package name.leesah.iki.gae;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GetUidServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String uid;
        String text;
        try {
            uid = Iki.getUid(req.getParameter("e"));
            text = "uid = " + uid;
            resp.setHeader("uid", uid);

        } catch (IkiException e) {
            text = e.getMessage();
        }

        resp.setContentType("text/plain");
        resp.getWriter().println(text);

    }
}
