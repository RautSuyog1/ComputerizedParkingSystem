import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServelet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public LoginServlet() 
	{
		super();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String RollNo = request.getParameter("rollNo");
		String Password = request.getParameter("pass");

		response.setContentType("text/html");
		LoginDAO dao = new LoginDAO();
		User user = dao.validateUser(RollNo, Password);
		if(user!= null) {
		    Connection conn = null;
		    Statement stmt = null;
		    ResultSet rs = null;
		    
		    try {
		      Class.forName("com.mysql.cj.jdbc.Driver");
		      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking_solution","root","Pass@123");
		      
		      // create a SQL statement to retrieve the user's history
		      String sql = "SELECT * FROM userhistory WHERE RollNo = ?";
		      PreparedStatement ps = conn.prepareStatement(sql);
		      ps.setString(1, RollNo);
		      
		      // execute the query
		      rs = ps.executeQuery();
		      
		      // generate an HTML table of the user's history
		      PrintWriter out = response.getWriter();
		      out.println("<html>"
		    		+ "<head>\r\n"
		      		+ "    <meta charset=\"UTF-8\" />\r\n"
		      		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
		      		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
		      		+ "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" />\r\n"
		      		+ "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css\" />    \r\n"
		      		+ "    <link rel=\"stylesheet\" href=\"styles.css\" />\r\n"
		      		+ "    <title>Parking Management System</title>\r\n"
		      		+ "\r\n"
		      		+ "\r\n"
		      		+"<style>\r\n"
		      		+ "table,th,td {\r\n"
		      		+ "  border: 1px solid;\r\n"
		      		+ "  padding: 10px 10px 10px ;\r\n "
		      		+ "}\r\n"
		      		+ " h2 {\r\n"
		      		+ "    margin-top: 2rem; /* adjust as needed */\r\n"
		      		+ "  }"
		      		+ "</style>"
		      		+ "</head><body> <div class=\"d-flex\" id=\"wrapper\">\r\n"
		      		+ "        <div class=\"bg-white\" id=\"sidebar-wrapper\">\r\n"
		      		+ "            <div class=\"sidebar-heading text-center py-4 primary-text fs-5 fw-bold text-uppercase border-bottom \" style=\"font-size: xx-small;\">Parking System</div>\r\n"
		      		+ "            <div class=\"list-group list-group-flush my-3 mt-5\">\r\n"
		      		
		      		+ " <a href=\"LandingPage.html\" class=\"list-group-item list-group-item-action bg-transparent text-danger fw-bold\"><i\r\n\"\r\n"
		    		+ "class=\"fas fa-power-off me-2\"></i>Logout</a>"
		    		+"</div></div>");
		      out.println("<div class=\"container\">\r\n"
		      		+ "  <div class=\"row\">\r\n"
		      		+ "    <div class=\"col\">"
		      		+ "<h2>IMCC Parking</h2></div></div>");
		      out.println("<div class=\"row\">\r\n"
		      		+ "    <div class=\"col\">\r\n");
		      		
		      out.println("<h3>User History for Roll No: " + RollNo + "</h3>");
		      out.println("<table class=\"table bg-white rounded shadow-sm  margin=5% height=100% width=100% table-hover \"  >");
		      out.println("<tr><th>Location</th><th>Date</th><th>User Name</th><th>Roll No</th><th>In Time</th><th>Out Time</th><th>Vehicle Type</th><th>Vehicle No</th><th>Payment Method</th><th>Remark</th></tr>");
		      while (rs.next()) {
		        out.println("<tr>");
		        out.println("<td>" + rs.getString("Location") + "</td>");
		        out.println("<td>" + rs.getString("Date") + "</td>");
		        out.println("<td>" + rs.getString("UserName") + "</td>");
		        out.println("<td>" + rs.getString("RollNo") + "</td>");
		        out.println("<td>" + rs.getString("InTime") + "</td>");
		        out.println("<td>" + rs.getString("OutTime") + "</td>");
		        out.println("<td>" + rs.getString("VehicleType") + "</td>");
		        out.println("<td>" + rs.getString("VehicleNo") + "</td>");
		        out.println("<td>" + rs.getString("PaymentMethod") + "</td>");
		        out.println("<td>" + rs.getString("Remark") + "</td>");
		        out.println("</tr>");
		      }
		      out.println("</table>");
		      out.println("</body></html>");
		      
		    } catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        if (rs != null) rs.close();
		        if (stmt != null) stmt.close();
		        if (conn != null) conn.close();
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }
		    }
		}else {
			response.sendRedirect("LandingPage.html");
			
		}
	}
	
}