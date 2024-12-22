import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/AHistoryServlet")
public class AHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the content type and encoding
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        
        // Create the HTML table header
        StringBuilder htmlTable = new StringBuilder();
        htmlTable.append("<html>\r\n"
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
        		+ "  padding: 10px 10px 10px 10px;\r\n "
        		+ "  width: 90%\r\n"
        		+ "  height: 100% \r\n"
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
        		+"</div></div>");
        htmlTable.append("<div class=\"container\">\r\n"
        		+ "  <div class=\"row\">\r\n"
        		+ "    <div class=\"col\">"
        		+ "<h2>Parking Lot History:</h2></div></div>");
        htmlTable.append("<div class=\"row\">\r\n"
        		+ "    <div class=\"col\">\r\n"
        		+ "      <table  class=\"table bg-white rounded shadow-sm margin=5%  table-hover \"  >");
        htmlTable.append("<tr><th>Location</th><th>Date</th><th>User Name</th><th>Roll No</th><th>In Time</th><th>Out Time</th><th>Vehicle Type</th><th>Vehicle No</th><th>Payment Method</th><th>Remark</th></tr>");

        // Fetch data from the database
        try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking_solution", "root", "Pass@123");
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM userhistory";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String location = resultSet.getString("Location");
                String date = resultSet.getString("Date");
                String userName = resultSet.getString("UserName");
                String rollNo = resultSet.getString("RollNo");
                String inTime = resultSet.getString("InTime");
                String outTime = resultSet.getString("OutTime");
                String vehicleType = resultSet.getString("VehicleType");
                String vehicleNo = resultSet.getString("VehicleNo");
                String paymentMethod = resultSet.getString("PaymentMethod");
                String remark = resultSet.getString("Remark");

                // Create a row in the HTML table
                htmlTable.append("<tr><td>").append(location).append("</td><td>").append(date).append("</td><td>").append(userName).append("</td><td>").append(rollNo).append("</td><td>").append(inTime).append("</td><td>").append(outTime).append("</td><td>").append(vehicleType).append("</td><td>").append(vehicleNo).append("</td><td>").append(paymentMethod).append("</td><td>").append(remark).append("</td></tr>");
            }

            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Close the HTML table
        htmlTable.append("</table>");

        // Write the HTML table to the response
        response.getWriter().write(htmlTable.toString());
    }
}
