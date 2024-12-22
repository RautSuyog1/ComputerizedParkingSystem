import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AEditServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        // Connect to database
        String jdbcUrl = "jdbc:mysql://localhost:3306/parking_solution";
        String username = "root";
        String password = "Pass@123";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);

            // Execute SQL query
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM user";
            ResultSet rs = stmt.executeQuery(sql);

            // Generate HTML table
            out.println("<html<head>\r\n"
    		+ "    <meta charset=\"UTF-8\" />\r\n"
    		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
    		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
    		+ "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" />\r\n"
    		+ "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css\" />    \r\n"
    		+ "    <link rel=\"stylesheet\" href=\"styles.css\" />\r\n"
    		+ "    <title>View/Edit Users</title>\r\n"
    		+ "\r\n"
    		+ "\r\n"
    		+"<style>\r\n"
    		+ "table,th,td {\r\n"
    		+ "  border: 1px solid;\r\n"
    		+ "  padding: 10px 10px 10px 10px;\r\n "
    		+ "  width: 90%\r\n"
    		+ "  height: 100% \r\n"
    		+ "  background-color: white;"
    		+ "}\r\n"
    		+ " h2 {\r\n"
    		+ "    margin-top: 2rem; \r\n"
    		+ "  }"
    		+ "</style>"
    		+ "</head><body> <div class=\"d-flex\" id=\"wrapper\">\r\n"
    		+ "        <div class=\"bg-white\" id=\"sidebar-wrapper\">\r\n"
    		+ "            <div class=\"sidebar-heading text-center py-4 primary-text fs-5 fw-bold text-uppercase border-bottom \" style=\"font-size: xx-small;\">Parking System</div>\r\n"
    		+ "            <div class=\"list-group list-group-flush my-3 mt-5\">\r\n"
    		+ " <a href=\"AHistoryServlet\" class=\"list-group-item list-group-item-action bg-transparent   fw-bold\"><i\r\n"
    		+ " class=\"fas fa-chart-line me-2\"></i>View History</a>\r\n"
    		+ " <a href=\"AEditServlet\" class=\"list-group-item list-group-item-action bg-transparent fw-bold\"><i\r\n\"\r\n"
    		+ "class=\"fas fa-trash-alt me-2\"></i>Delete User</a>"
    		+ " <a href=\"LandingPage.html\" class=\"list-group-item list-group-item-action bg-transparent text-danger fw-bold\"><i\r\n\"\r\n"
    		+ "class=\"fas fa-power-off me-2\"></i>Logout</a>"
    		+"</div></div>"
    		+ "</head><body>");		
            out.println("<div class=\"container\">\r\n"
            		+ "  <div class=\"row\">\r\n"
            		+ "    <div class=\"col\">"
            		+ "<h2>Users present in database:</h2></div></div>");
    		out.println("<div class=\"row\">\r\n"
            		+ "    <div class=\"col\">\r\n"
            		+ "      <table  class=\"table bg-white rounded shadow-sm margin=5%  table-hover \"  >");
            out.println("<tr><th>UserName</th><th>RollNo</th><th>UClass</th><th>VehicleType</th><th>VehicleNo</th><th>EmailId</th><th>ContactNo</th><th>Delete</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("UserName") + "</td>");
                out.println("<td>" + rs.getInt("RollNo") + "</td>");
                out.println("<td>" + rs.getString("UClass") + "</td>");
                out.println("<td>" + rs.getString("VehicleType") + "</td>");
                out.println("<td>" + rs.getString("VehicleNo") + "</td>");
                out.println("<td>" + rs.getString("EmailId") + "</td>");
                out.println("<td>" + rs.getString("ContactNo") + "</td>");
                out.println("<td><form action='DeleteServlet' method='post'><input type='hidden' name='id' value='" + rs.getInt("RollNo") + "'/><input type='submit' value='Delete'/></form></td>");
                out.println("</tr>");
            }
            out.println("</table>");

            // Close resources
            rs.close();
            stmt.close();
            conn.close();

            out.println("</body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

