import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/RecordServlet")
public class RecordServlet extends HttpServlet {

    
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve data from the HTML form
        String location = request.getParameter("Location");;
        String date = request.getParameter("Date");
        String rollNo = request.getParameter("RollNo");
        String inTime = request.getParameter("InTime");
        String paymentMethod = request.getParameter("PaymentMethod");
        
        
        try {
            String url = "jdbc:mysql://localhost:3306/parking_solution";
            String user = "root";
            String password = "Pass@123";
            String Query= "SELECT UserName, VehicleType, VehicleNo FROM user WHERE RollNo = ?";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user,password);

            // Fetch data from the user table for a specific RollNo
            PreparedStatement pstmt = conn.prepareStatement(Query);
            pstmt.setString(1, rollNo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String userName = rs.getString("UserName");
                String vehicleType = rs.getString("VehicleType");
                String vehicleNo = rs.getString("VehicleNo");;
                String insertsql = "INSERT INTO userrecords (Location, Date, UserName, RollNo, InTime, VehicleType, VehicleNo, PaymentMethod) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(insertsql);
            // Set the parameters for the SQL statement
            statement.setString(1, location);
 
            statement.setString(2, date);
            statement.setString(3, userName);
            statement.setString(4, rollNo);
            statement.setString(5, inTime);
           // statement.setString(3, uClass);
            statement.setString(6, vehicleType);
            
            statement.setString(7, vehicleNo);
            //statement.setString(6, emailId);
            //statement.setString(7, contactNo);
            statement.setString(8, paymentMethod);
            statement.executeUpdate();
            // Execute the SQL statement to insert data into userrecords table
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0 ) {
                // Data inserted successfully
                response.getWriter().println("<h1>Record added successfully!</h1>");
            } else {
                // Error occurred while inserting data
                response.getWriter().println("<h1>Error: Record not added!</h1>");
            }
            statement.close();
            conn.close();
            }
            // Close the database connection
            rs.close();
          //  conn.close();
        } catch (Exception ex) {
            // Exception occurred while connecting to or querying the database
            response.getWriter().println("<h1> Vehicle Parked.....</h1>");
        }
    }
}
