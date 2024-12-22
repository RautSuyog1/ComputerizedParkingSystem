import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/UserHistoryServlet")
public class UserHistoryServlet extends HttpServlet 
{

    private static final long serialVersionUID = 1L;
    
    public UserHistoryServlet() {
        super();
    }
    
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get input values from request parameters
        String rollNo = request.getParameter("rollNo");
        String out= rollNo;
        String outTime = request.getParameter("outTime");
        String remark = request.getParameter("remark");
        
        // Database connection details
        String dbUrl = "jdbc:mysql://localhost:3306/parking_solution";
        String dbUsername = "root";
        String dbPassword = "Pass@123";

        // SQL query to select data from userrecords table using Roll No
        String selectQuery = "SELECT Location, Date, UserName, RollNo, InTime, VehicleType, VehicleNo, PaymentMethod  FROM userrecords WHERE RollNo = ?";

        // SQL query to insert data into userhistory table
        String insertQuery = "INSERT INTO userhistory (Location, Date, UserName, RollNo, InTime, VehicleType, VehicleNo, PaymentMethod, OutTime, Remark) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
          // Connect to database
          Class.forName("com.mysql.cj.jdbc.Driver");
          Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

          // Prepare select statement with Roll No parameter
          PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
          selectStmt.setString(1, rollNo);
          
          // Execute select statement
          ResultSet rs = selectStmt.executeQuery();
          
          // If data is found for given Roll No
          if (rs.next()) {
            // Get all fields from result set
            String location = rs.getString("Location");
            String date = rs.getString("Date");
            String userName = rs.getString("UserName");
            String inTime = rs.getString("InTime");
            String vehicleType = rs.getString("VehicleType");
            String vehicleNo = rs.getString("VehicleNo");
            String paymentMethod = rs.getString("PaymentMethod");

            // Prepare insert statement with all fields and input values for OutTime and Remark
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, location);
            insertStmt.setString(2, date);
            insertStmt.setString(3, userName);
            insertStmt.setString(4, rollNo);
            insertStmt.setString(5, inTime);
            insertStmt.setString(6, vehicleType);
            insertStmt.setString(7, vehicleNo);
            insertStmt.setString(8, paymentMethod);
            insertStmt.setString(9, outTime);
            insertStmt.setString(10, remark);

            // Execute insert statement
            int rowsInserted = insertStmt.executeUpdate();
            
            // If insert was successful, delete the corresponding row from userrecords table
            if (rowsInserted > 0) {
              String deleteQuery = "DELETE FROM userrecords WHERE RollNo = ?";
              PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
              deleteStmt.setString(1, rollNo);
              deleteStmt.executeUpdate();
            }
          }

          // Close database connection and resources
          rs.close();
          selectStmt.close();
          conn.close();

          // Send success response to client
          response.setContentType("text/html");
          response.getWriter().println("<html><head><title>removed</title></head><body>");
          response.getWriter().println("<h2> Roll No:"+out+" You can go now out of the IMCC Campus</h2>");
          response.getWriter().println(" ");
          response.getWriter().println(" <a href=displayRecordServlet>ManagerHome</a>");

        } catch (Exception e) {
        	System.out.println(e);
        	
        	response.setContentType("text/html");
            response.getWriter().println("<html><head><title> Fail to remove</title></head><body>");
            response.getWriter().println("<h2> Error occurred "+e+"</h2>");
            response.getWriter().println(" ");
            response.getWriter().println(" <a href=manager.html>ManagerHome</a>");
       }
    }
}