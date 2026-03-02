package game;

import java.io.IOException;
import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet; 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
import jakarta.servlet.http.HttpSession; 

@WebServlet(name = "GameServlet", urlPatterns = {"/gameServlet"}) 
public class GameServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        GameBean gameBean = (GameBean) session.getAttribute("gameBean");

        
        if (gameBean == null) {
            gameBean = new GameBean();
            session.setAttribute("gameBean", gameBean);
            request.getRequestDispatcher("/game.jsp").forward(request, response);
            return;
        }

     
        if (gameBean.getWinner() == null) {
            try {
         
                int line = Integer.parseInt(request.getParameter("Line"));
                int col = Integer.parseInt(request.getParameter("Col"));

  
                boolean moveMade = gameBean.makeMove(line, col);

             
                if (moveMade && gameBean.getWinner() == null && gameBean.getCurrentPlayer() == GameBean.GamePlayer.COMPUTER) {
                    gameBean.makeComputerMove();
                }

            } catch (NumberFormatException e) {
               
                System.err.println("Error de formato en parámetros Line o Col: " + e.getMessage());
            }
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
        return "Procesa los movimientos del juego.";
    }
}