import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLreader {
	
  private Connection connect = null;
  private PreparedStatement statement = null;
  private ResultSet resultSet = null;
  private String user=null;
  private String pw=null;
  private String url=null;
  public SQLreader(String u, String p, String rl) {
	  user=u;
	  pw=p;
	  url=rl;
  }
  public boolean evaluateUser(String userInput, String pwInput) throws Exception {
	    try {
	    	//Setup DB Connection
	      Class.forName("com.mysql.cj.jdbc.Driver");
	      connect =DriverManager.getConnection("jdbc:mysql://"+url+"/?user="+user+"&password="+pw);	    
	      statement = connect.prepareStatement("SELECT EXISTS ( SELECT * FROM logintable.users WHERE users = ? AND passwords = ? )");
	      
	      // Evaluates a true/false resultset from Exists command
	      statement.setString(1, userInput);
	      statement.setString(2, pwInput);
	      resultSet = statement.executeQuery();
	      return evaluatePassword(resultSet);
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
	      statement = connect.prepareStatement("INSERT INTO logintable.users VALUES (?,?)");
	      
	      //Input given user into DB
	      statement.setString(1,user);
	      statement.setString(2, password);
	      statement.executeUpdate();
	  } catch (Exception e) {
		  throw e;
	  } finally {
	    	close();
	    }
  }
  private boolean evaluatePassword(ResultSet resultSet) throws SQLException{
	  while (resultSet.next()) {
		  boolean match = resultSet.getBoolean(1);
			  return match;
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