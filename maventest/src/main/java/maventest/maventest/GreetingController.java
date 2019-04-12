package maventest.maventest;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

@RestController
@PropertySource("classpath:app.properties")
public class GreetingController {

	@Autowired
	Environment env;	
	private SQLreader dbReader;
	private String dbUser;
	private String dbPass;
	private String dbURL;
	
    @RequestMapping("/login")
    public Greeting greeting(@RequestParam Map<String,String> loginInfo) throws Exception {
    	dbUser = env.getProperty("dbUser");
    	dbPass = env.getProperty("dbPass");
    	dbURL = env.getProperty("dbURL");
    	dbReader = new SQLreader(dbUser,dbPass,dbURL);
        String user=loginInfo.get("user");
        System.out.println(user);
        String pass=loginInfo.get("pass");
        System.out.println(pass);
        return new Greeting(dbReader.evaluateUser(user, pass)); //Returns JSON containing login success and login Message 
    }
}