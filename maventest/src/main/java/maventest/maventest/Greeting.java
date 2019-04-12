package maventest.maventest;

public class Greeting {

    private final boolean success;
    private final String content;

    public Greeting(boolean success) {
        this.success = success;
        if (success) {
        	this.content = "You have successfully logged in";
        }
        else {
        	content = "Invalid login attempt";
        }
    }

    public boolean getSuccess() {
        return success;
    }

    public String getContent() {
        return content;
    }
}