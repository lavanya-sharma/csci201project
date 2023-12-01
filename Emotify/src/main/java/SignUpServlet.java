

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SignUp")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("text/html");    
        String path = getServletContext().getRealPath("signup.html");
        String htmlContent = readHtmlFile(path);
        PrintWriter out = response.getWriter();
        out.print(htmlContent);
        out.flush();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
//		System.out.println("username: " + username);
//		System.out.println("password: " + password);
//		System.out.println("email: " + email);
		
		JDBCConnector jdbc = new JDBCConnector();
		UserList userList = (UserList) session.getAttribute("UserList");
		userList.setUserList(jdbc.populateUserList());
    	session.setAttribute("UserList", userList);
			
		int index = email.indexOf('@');
		String errorMessage = "<p style='color: red;'>Something went wrong. Please try again.</p>";
		try {
			jdbc.insertNewUser(username, password, email);
			User newUser = new User(username, password, email);
			userList.addUser(newUser);
			session.setAttribute("UserList", userList);
			session.setAttribute("user", newUser);
			response.sendRedirect("Home");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private String readHtmlFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading HTML file.";
        }
    }

}
