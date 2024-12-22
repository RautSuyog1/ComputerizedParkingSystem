import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DeleteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Retrieve RollNo parameter from request
        int rollNo = Integer.parseInt(request.getParameter("id"));

        // Connect to database
        String jdbcUrl = "jdbc:mysql://localhost:3306/parking_solution";
        String username = "root";
        String password = "Pass@123";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);

            // Execute SQL query
            String sql = "DELETE FROM user WHERE RollNo=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, rollNo);
            stmt.executeUpdate();

            // Close resources
            stmt.close();
            conn.close();

            // Redirect back to user table page
            response.sendRedirect("AEditServlet");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
