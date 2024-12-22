import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

	private static final String dbDriver = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/parking_solution";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "Pass@123";
	Connection dbCon;
	
	private boolean connectToDtbc()throws SQLException,ClassNotFoundException
	{
	  Class.forName(dbDriver);
	  dbCon = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
	  System.out.println("connected");
	  if(dbCon != null) {
	
		  return true;
	  }
	return false;
	}
	
	public User validateUser(String rollNo, String password) {
		User user = null;
		try {
			if(connectToDtbc()) {
				String query = "SELECT * FROM user WHERE RollNo = ? AND Password = ?";
				PreparedStatement stmt = dbCon.prepareStatement(query);
				stmt.setString(1, rollNo);
				stmt.setString(2, password);
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					user = new User();
					//user.setRollNo(Integer.parseInt(rs.getString(2)));
					//String rno = rs.getString(2);
					//user.setUserName(rs.getString(1));
					//System.out.println(user.getRollNo());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
