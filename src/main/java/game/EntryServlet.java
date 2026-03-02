package game;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "EntryServlet", urlPatterns = {"/EntryServlet"})
public class EntryServlet extends HttpServlet {


	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        GameBean gameBean = (GameBean) session.getAttribute("gameBean");

        if (gameBean == null) {
            gameBean = new GameBean();
            session.setAttribute("gameBean", gameBean);
        }

        if (request.getParameter("Replay")!= null) {
            gameBean.initGame();
        }
        
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Procesa la respuesta del formulario de inicio.";
    }
}