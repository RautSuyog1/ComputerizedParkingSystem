import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/displayRecordServlet")
public class displayRecordServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	String dbDriver = "com.mysql.cj.jdbc.Driver";
        	String dbURL = "jdbc:mysql://localhost:3306/parking_solution";
        	String userName ="root";
        	String pwd = "Pass@123";
        	String query = "SELECT Location,Date,UserName,RollNo,InTime,VehicleType,VehicleNo,PaymentMethod FROM userrecords";
//            // Fetch records from database 
//            // Format records as HTML
            
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            Class.forName(dbDriver);
            Connection dbCon = DriverManager.getConnection(dbURL, userName, pwd);
            PreparedStatement ps= dbCon.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            out.println("<html>\r\n"
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
            		+ "            <a href=\"displayRecordServlet\" class=\"list-group-item list-group-item-action bg-transparent   fw-bold\"><i\r\n"
            		+ "                    class=\"fas fa-chart-line me-2\"></i>View Parking</a>\r\n"
            		+"<a href=\"HistoryServlet\" class=\"list-group-item list-group-item-action bg-transparent fw-bold\"><i\r\n"
            		+ "                class=\"fas fa-project-diagram me-2\"></i>Parking History</a>"
            		+ "            <a href=\"manager-addpage.html\" class=\"list-group-item list-group-item-action bg-transparent   fw-bold\"><i\r\n"
            		+ "                    class=\"fas fa-project-diagram me-2\"></i>Vehical In</a>"
            		+"<a href=\"manager-removepage.html\" class=\"list-group-item list-group-item-action bg-transparent   fw-bold\"><i\r\n\"\r\n"
            		+ "            		+ \"                    class=\"fas fa-project-diagram me-2\"></i>Vehical Out</a>\""
            		+ " <a href=\"LandingPage.html\" class=\"list-group-item list-group-item-action bg-transparent text-danger fw-bold ><iclass=\"fas fa-power-off me-2\"></i>Logout</a>"
            		+"</div></div>");
            out.println("<div class=\"container\">\r\n"
            		+ "  <div class=\"row\">\r\n"
            		+ "    <div class=\"col\">"
            		+ "<h2>Vehicles in Parking Lot:</h2></div></div>");
            out.println("<div class=\"row\">\r\n"
            		+ "    <div class=\"col\">\r\n"
            		+ "      <table  class=\"table bg-white rounded shadow-sm  margin=5% height=100% width=100% table-hover \"  >");
            out.println("<br><tr><th>Location</th><th>Date</th><th>StudentName</th><th>RollNo</th><th>In Time</th><th>Vehicle Type</th><th>Vehicle Number</th><th>Payment Method</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td style=height: 5%;>" + rs.getInt(1) + "</td>");
                out.println("<td style=height: 5%;>" + rs.getString(2) + "</td>");
                out.println("<td style=height: 5%;>" + rs.getString(3) + "</td>");
                out.println("<td style=height: 5%;>" + rs.getString(4) + "</td>");
                out.println("<td style=height: 5%;>" + rs.getString(5) + "</td>");
                out.println("<td style=height: 5%;>" + rs.getString(6) + "</td>");
                out.println("<td style=height: 5%;>" + rs.getString(7) + "</td>");
                out.println("<td style=height: 5%;>" + rs.getString(8) + "</td>");
                out.println("</tr>");
            }
            out.println("</table></div>\r\n"
            		+ "  </div>\r\n"
            		+ "</div>\r\n");
            out.println("</body></html>");
            
            rs.close();
            ps.close();
            dbCon.close();
        } catch (Exception e) {
            e.printStackTrace();
            
        } 
        
    }
}