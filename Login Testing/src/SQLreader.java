import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLreader {
	
  private Connection connect = null;
  private Statement statement = null;
  private ResultSet resultSet = null;
  
  public boolean evaluateUser(String userInput, String pwInput) throws Exception {
	    try {
	    	//Setup DB Connection
	      Class.forName("com.mysql.cj.jdbc.Driver");
	      connect =DriverManager.getConnection("jdbc:mysql://localhost:3306/logintable?" +
	              "user=root&password=$cnrtdz1Scnrtdz1");	    
	      statement = connect.createStatement();
	      
	      // Grab the user from the database and check pw match
	      resultSet = statement.executeQuery("select * from logintable.users where users=\""+userInput+"\"");
	      return evaluatePassword(resultSet,pwInput);
	    } catch (Exception e) {
	      throw e;
	    }finally {
	    	close();
	    }
	  }
  public void addToDB(String user, String password) throws Exception {
	  try {
	      // Setup DB Connection
	      Class.forName("com.mysql.cj.jdbc.Driver");
	      connect =DriverManager.getConnection("jdbc:mysql://localhost/logintable?" +
	              "user=root&password=$cnrtdz1Scnrtdz1");
	      statement = connect.createStatement();
	      
	      //Input given user into DB
	      statement.executeUpdate("INSERT INTO logintable.users VALUES (\""+user+"\",\""+password+"\")");
	  } catch (Exception e) {
		  throw e;
	  } finally {
	    	close();
	    }
  }
  private boolean evaluatePassword(ResultSet resultSet, String pwInput) throws SQLException{
	  while (resultSet.next()) {
		  String password = resultSet.getString("passwords");
		  if (password.equals(pwInput)) {
			  return true;
		  }
	  }
	  return false;
  }
  // You need to close the resultSet
  private void close() throws Exception{
      if (resultSet != null) {
        resultSet.close();
      }
      if (statement != null) {
        statement.close();
      }
      if (connect != null) {
        connect.close();
      }

  }

}