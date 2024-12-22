import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
	String dbDriver = "com.mysql.cj.jdbc.Driver";
	String dbURL = "jdbc:mysql://localhost:3306/parking_solution";
	String userName ="root";
	String pwd = "Pass@123";
	Connection dbCon;

	private boolean connectToDtbc()throws SQLException,ClassNotFoundException
	{
	  Class.forName(dbDriver);
	  dbCon = DriverManager.getConnection(dbURL,userName,pwd);
	  return true;
	  
	}
	  public boolean insertRecord(User userObj)
	  {
		  String insertQueryStr = "INSERT into user VALUES(?,?,?,?,?,?,?,?)";
		  try
		  {
			  if(connectToDtbc())
			  {
				  PreparedStatement ppStmt = dbCon.prepareStatement(insertQueryStr);
				  ppStmt.setString(1,userObj.getUserName());
				  ppStmt.setInt(2,userObj.getRollNo());
				  ppStmt.setString(3,userObj.getUClass());
				  ppStmt.setString(4,userObj.getVehicleType());
				  ppStmt.setString(5,userObj.getVehicleNo());
				  ppStmt.setString(6,userObj.getEmailId());
				  ppStmt.setString(7,userObj.getContactNo());
				  ppStmt.setString(8,userObj.getPassword());
				  ppStmt.executeUpdate();
				  ppStmt.close();
				  dbCon.close();
				  return true;
			  }
			  else return false;
				  
		  }
		  
		  catch(Exception e)
		  {
			  StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            String exceptionAsString = sw.toString();
	            System.out.println(exceptionAsString);
			  
		  }
		  //return false;
		return false;
	}
}
