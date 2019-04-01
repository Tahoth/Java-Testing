	import java.awt.Color;
	import java.awt.Font;
	import java.awt.event.*;
	import java.sql.SQLIntegrityConstraintViolationException;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPasswordField;
	import javax.swing.JTextField;

public class LoginBox extends JFrame {
	// Variables
	JLabel userLabel,passwordLabel,boxLabel;
	JTextField userField;
	JPasswordField passField;
	JButton loginButton,signUpButton;
	SQLreader dbReader;
		//Constructor
	LoginBox() {
			//SQL Database Connection
		dbReader = new SQLreader();
			//UI components
		JFrame frame = new JFrame("Login Box");
		userLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		boxLabel = new JLabel("Login here");
		boxLabel.setForeground(Color.blue);
		boxLabel.setFont(new Font("Serif",Font.BOLD, 20));
		userField = new JTextField();
		passField = new JPasswordField();
		loginButton = new JButton("Login");
			//Assign button actions
		loginButton.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e){
				  loginButtonAction();
			  }});
		signUpButton = new JButton("Sign up");
		signUpButton.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e){
				  signUpButtonAction();
			  }});
			//Formatting
		boxLabel.setBounds(250, 30, 400, 30);
		userLabel.setBounds(150, 70, 200, 30);
		passwordLabel.setBounds(150, 110, 200, 30);
		userField.setBounds(300, 70, 200, 30);
		passField.setBounds(300, 110, 200, 30);
		loginButton.setBounds(150, 160, 100, 30);
		signUpButton.setBounds(300, 160, 100, 30);		
		frame.add(boxLabel);
		frame.add(userLabel);
		frame.add(userField);
		frame.add(passwordLabel);
		frame.add(passField);
		frame.add(loginButton);
		frame.add(signUpButton);		
		frame.setSize(600,400);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	
	public void loginButtonAction() {
		String username = userField.getText();
		char[] pass = passField.getPassword();
		try { //If user exists and password matches, display welcome
			if(dbReader.evaluateUser(username,String.copyValueOf(pass))) {
			Welcome wel = new Welcome();
			wel.setVisible(true);
			JLabel welLabel = new JLabel("Welcome: "+username);
			wel.getContentPane().add(welLabel);
			}
			else { //Inform user of incorrect login
				JOptionPane.showMessageDialog(this, "Incorrect Login or Password", "Error",JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void signUpButtonAction(){
		String username = userField.getText();
		char[] pass = passField.getPassword();
		try {  //Insert User into database
			dbReader.addToDB(username, String.copyValueOf(pass));
			JOptionPane.showMessageDialog(this, "User Created Successfully", "Success!",JOptionPane.INFORMATION_MESSAGE);
		}catch (SQLIntegrityConstraintViolationException e) //Detects duplicate user
		  { 
			JOptionPane.showMessageDialog(this, "User Already Exists", "Error",JOptionPane.ERROR_MESSAGE);
		  } catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		new LoginBox();
	}

}
