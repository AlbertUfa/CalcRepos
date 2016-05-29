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

import calc.CalcOperations;
import calc.OperationType;

/**
 * Servlet implementation class CalcServlet
 */
//Рабочий сервлет
@WebServlet("/CalcServlet")
public class CalcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
		   //Объявлен объект типа ArrayList со ссылкой listOperatoins, 
		   //который хранит данные типа String для записи в него сессий
		    private ArrayList<String> listOperations = new ArrayList<String>();


		    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		            throws ServletException, IOException {


		        response.setContentType("text/html;charset=UTF-8");
		        PrintWriter out = response.getWriter();

		        out.println("<html>");
		        out.println("<head>");
		        out.println("<title>Servlet CalcServlet</title>");
		        out.println("</head>");
		        out.println("<body>");

		        try {

		            // считывание параметров
// было у автора    double one = Double.valueOf(request.getParameter("one").toString()).doubleValue();
//		            double two = Double.valueOf(request.getParameter("two").toString()).doubleValue();
		        	double one = Double.valueOf(request.getParameter("one"));
		            double two = Double.valueOf(request.getParameter("two"));
		            String opearation = String.valueOf(request.getParameter("operation"));

		            // определение или создание сессии, если ее нет
		            HttpSession session = request.getSession(true);

		            // получаем тип операции, какую клиент хочет сделать
		            //при помощи ValueOf из Enum 
		            OperationType operType = OperationType.valueOf(opearation.toUpperCase());

		            // калькуляция. Подсчитывем результат, при помощи метода calcResult(ниже)
		            //куда мы передаем тип операции и два числа(значения)
		            //Кстати, этот класс не нужно прописывать где-то в web.xml, так как это
		            //самостоятельный класс. CalcServlet вытягивает его, черз импорт напрямую. В котором 
		            //уже вытягивается необходимый в определнный момент метод(суммиров., др)
		            double result = calcResult(operType, one, two);
		            
//		          //Здесь определяем - если сессия новая, то создаем новую. Чтобы сессия от
//		          //предыдущего другого пользователя сюда не включалась.
		  
		            //Если новый запрос/пользователь, то очищаем ArraList listOperations
		            // и для новой сессии создаем новый список
		            if (session.isNew()) {//если новая
		                listOperations.clear();//то очищаем наш ArrayList от предыдущей
		            } 
//		            else { // иначе, получаем список из атрибутов сессии
//		                listOperations = (ArrayList<String>) session.getAttribute("formula");
//		            }//через эти атрибуты, при помощи которых к ним получает доступ
//		            //другой(проверочный) сервлет CheckOperationsServlet. Потому как сам
//		            //Arralyst инкапсулирован (private).          

		            // добавление новой операции в список и атрибут сессии
		            //То есть, через one и two данные? а тип операции и ее значок (StringValue)
		            //при помощи operType.getStringValue()
		            listOperations.add(one + " " + operType.getStringValue() + " " + two + " = " + result);
		            //И сразу записывается в сессию в качестве атрибута под названием "formula"
		            //В атрибут записывается весь список listOperations
		            session.setAttribute("formula", listOperations);
		            


		            // вывод всех операций, id, размер
		            out.println("<h1>ID вашей сессии равен: " + session.getId() + "</h1>");
		            out.println("<h3>Список операций (всего:" + listOperations.size() + ") </h3>");
		            //и черз цикл выводим все значения из этого списка операций
		            for (String oper : listOperations) {
		                out.println("<h3>" + oper + "</h3>");
		            }



		        } catch (Exception ex) {
		            // предупреждение пользователю в случае ошибки
		        	out.println("<h3 style=\"color:red;\">Возникла ошибка. Проверьте параметры</h3>");
		            out.println("<h3>Имена параметров должны быть one, two, operation</h3>");
		            out.println("<h3>Operation должен принимать 1 из 4 значений: add, subtract, multiply, divide</h3>");
		            out.println("<h3>Значения one и two должны быть числами</h3>");
		            out.println("<h1>Пример</h1>");
		            out.println("<h3>?one=1&two=3&operation=add</h3>");

		        } finally {
		        	out.println("</body>");
		            out.println("</html>");
		            out.close();
		        }
		    }

		    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

		    
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
		        return "Сервлет - калькулятор";
		    }// </editor-fold>

		    //Метод для калькуляции, использующий методы класса CalcOperations
		    // калькуляция
		    private double calcResult(OperationType operType, double one, double two) {
		        double result = 0;
		        switch (operType) {
		            case ADD: {
		                result = CalcOperations.add(one, two);
		                break;
		            }
		            case SUBTRACT: {
		                result = CalcOperations.subtract(one, two);
		                break;
		            }

		            case DIVIDE: {
		                result = CalcOperations.divide(one, two);
		                break;
		            }

		            case MULTIPLY: {
		                result = CalcOperations.multiply(one, two);
		                break;
		            }

		        }

		        return result;
		    }
		}
