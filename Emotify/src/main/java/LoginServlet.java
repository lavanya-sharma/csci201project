

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

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("text/html");    
        String path = getServletContext().getRealPath("login.html");
        String htmlContent = readHtmlFile(path);
        PrintWriter out = response.getWriter();
        out.print(htmlContent);
        out.flush();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		
		JDBCConnector jdbc = new JDBCConnector();
		UserList userList = (UserList) session.getAttribute("UserList");
		userList.setUserList(jdbc.populateUserList());
    	session.setAttribute("UserList", userList);
		
		if(jdbc.validUser(username, password)) {
			for(int i = 0; i < userList.getUserList().size(); i++) {
				if(username.equals(userList.getUserList().get(i).getUsername()) && password.equals(userList.getUserList().get(i).getPassword())) {
					session.setAttribute("user", userList.getUserList().get(i));
				}
			}
			// sends to the Home Servlet with path @WebServlet("/Home") update as necessary
			response.sendRedirect("Home");
		}
		else {
			System.out.println("got here");
			response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            String errorMessage = "<p style='color: red;'>Invalid Credentials. Please try again.</p>";
            String path = getServletContext().getRealPath("login.html");
            String htmlContent = readHtmlFile(path);
            htmlContent = htmlContent.replace("<!--#error_message1#-->", errorMessage);
            out.print(htmlContent);
            out.flush();
		}
	
		
		
		
//		else if(pressed_button.equals("signup_button")) {
//			String signup_email = request.getParameter("signup_email");
//			String signup_username = request.getParameter("signup_username");
//			String signup_password = request.getParameter("signup_password");
//			String confirm_password = request.getParameter("confirm_password");
//			
//			int index = signup_email.indexOf('@');
//			String errorMessage = "<p style='color: red;'>Something went wrong. Please try again.</p>";
//			if(index == -1 || (index != -1 && signup_email.length() <= index+1)) {
//				errorMessage = "<p style='color: red;'>Invalid email. Please try again.</p>";
//				response.setContentType("text/html");
//	            PrintWriter out = response.getWriter();
//	            String path = getServletContext().getRealPath("login.html");
//	            String htmlContent = readHtmlFile(path);
//	            htmlContent = htmlContent.replace("<!--#error_message2#-->", errorMessage);
//	            out.print(htmlContent);
//	            out.flush();
//			}
//			else if(!signup_password.equals(confirm_password)) {
//				errorMessage = "<p style='color: red;'>Passwords do not match. Please try again.</p>";
//				response.setContentType("text/html");
//	            PrintWriter out = response.getWriter();
//	            String path = getServletContext().getRealPath("login.html");
//	            String htmlContent = readHtmlFile(path);
//	            htmlContent = htmlContent.replace("<!--#error_message2#-->", errorMessage);
//	            out.print(htmlContent);
//	            out.flush();
//			}
//			else {
//				try {
//					jdbc.insertNewUser(signup_username, signup_password, signup_email);
//					User newUser = new User(signup_username, signup_password, signup_email);
//					userList.addUser(newUser);
//					session.setAttribute("UserList", userList);
//					session.setAttribute("user", newUser);
//					response.sendRedirect("Home");
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//			
//			
//		}
		
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
