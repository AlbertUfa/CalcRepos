package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Сервлет для проверки
@WebServlet("/CheckOperationsServlet")
public class CheckOperationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckAttributeServlet</title>");
            out.println("</head>");
            out.println("<body>");

            //Получаем сессию(доступ) из сервлета CalcServlet
            HttpSession session = request.getSession(true);
            //Получаем атрибуты сессии
            Object attr = session.getAttribute("formula");

            if (attr instanceof ArrayList){
                ArrayList list = (ArrayList) attr;//перобразовываем атрибут в ArrayList(новый, не из CalcServlet) 
                out.println("<h1>Список операций:</h1>");//при помощи такого нисходящего преобразования
                for (Object str : list) {//и через цикл прогоняем вывод результата в Html
                    out.println("<h3>"+str+"</h3>");
                }
                
            }else{
                out.println("<h1>Операции не найдены</h1>");
            }

        } finally {
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
