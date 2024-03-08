package Controllers;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("/hello-management")
public class HelloController {

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/hello")
    public static void hello(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws ServletException, IOException {
        //HttpSession session = req.getSession();
        //int count = session.getAttribute("count") == null ? 0 : (int) session.getAttribute("count");
        //count++;
        //session.setAttribute("count", count);

        // Dispatcher vers la vue "hello.html"
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/hello.html");
        dispatcher.forward(req, resp);
    }
}
