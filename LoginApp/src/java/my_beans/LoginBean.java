package my_beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean {

    private boolean logged;
    private String name;
    private String email;
    private String password;
    private String dbusername;
    private String error;
    private String passwordCompare;
    private String[] interests;
    private boolean verifyDA;
    private int numOfTries = 0;
    private int maxTries = 3;
    private String updateMessage;

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPasswordCompare() {
        return passwordCompare;
    }

    public void setPasswordCompare(String passwordCompare) {
        this.passwordCompare = passwordCompare;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public String getDbusername() {
        return dbusername;
    }
    private String dbpassword;

    /**
     * @return the response
     */
    public String getResponse() {
        numOfTries++;
        if (numOfTries <= maxTries) {
            if ((name != null) && (verifyDA)) {
                numOfTries = 0;
                return "Login Successful";
            } else {
                return "Sorry, username or password is incorrect.";
            }
        } else {
            return "You have been locked out!!!";
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    //VALIDATION
    public boolean CheckName() {
        boolean result = true;
        if (name.equals("")) {
            error = "Please enter your name";
            result = false;
        }

        return result;
    }

    public boolean CheckEmail() {
        boolean result = true;
        boolean availEmail = true;

        //validate email
        String emailStr = email;


        if (emailStr.equals("")) {
            error = "Please enter an email address";
            result = false;
        }


        int atPosition = emailStr.indexOf("@");
        int atPosition2 = emailStr.lastIndexOf("@");


        //validate @ signs

        if (emailStr != "" && atPosition == -1) {
            error = "Email not formatted correctly, must have an @ symbol";   // must have an @ sign
            result = false;
        }

        if (atPosition == 0) {
            error = "Email not formatted correctly, @ symbol cannot be at beginning";   // no @ at the beginning
            result = false;
        }

        if (emailStr != "" && atPosition2 == emailStr.length() - 1) {
            error = "Email not formatted correctly, @ symbol cannot be at end";       // no @ at the end
            result = false;
        }

        if (atPosition2 != atPosition) {
            error = "Email not formatted correctly, must only have one @ sign";       // only one @ sign
            result = false;
        }


        int firstP = emailStr.indexOf('.');
        int lastP = emailStr.lastIndexOf('.');

        //validate periods

        if (emailStr != "" && firstP == -1) {
            error = "Email not formatted correctly, must include at least one period";
            result = false;
        }
        if (firstP == 0) {
            error = "Email not formatted correctly, cannot start with period";           // no . at the beginning
            result = false;
        }

        if (emailStr != "" && lastP == emailStr.length() - 1) {
            error = "Email not formatted correctly, cannot end with period";     // no . at the end
            result = false;
        }

        if (firstP == atPosition + 1) {
            error = "Email not formatted correclty, cannot have period directly after @ symbol"; // no . directly after @ symbol
            result = false;
        }
        if (firstP == atPosition - 1) {
            error = "Email not formatted correclty, cannot have period directly before @ symbol"; // no . directly after @ symbol
            result = false;
        }

        //validate no spaces
        int spaces = emailStr.indexOf(" ");
        if (spaces != -1) {
            error = "Email not formatted correctly, cannot include any spaces";
            result = false;
        }

        //check if this email is not used in the DB
        availEmail = LoginBeanDA.getEmailAvailableFromDB(this);
        result = availEmail;
        if (result == false) {
            error = "This email has been used already, please change email";
        }
        return result;
    }

    public boolean CheckPassword() {
        error = "";
        boolean result = true;
        if (password.length() < 6 || password.length() > 12) {
            error = "Password must be between 6 and 12 characters";
            result = false;
        }
        if (!password.equals(passwordCompare)) {
            result = false;
            error = "The passwords you entered are not the same.";
        }

        return result;
    }

    public String logOut() {
        logged = false;
        return "index";
    }

    public String logIn() {
        verifyDA = LoginBeanDA.getProfileFromDB(this);
        if (verifyDA) {
            logged = true;
        } else {
          //  error = "oops!...  This email or password is not in our DataBase, please try again.";
            //logIn();
            return "SignIn";
        }
        return "index";
    }

    public String persist() {
        if (CheckName() && CheckEmail() && CheckPassword()) {
            LoginBeanDA.storeCustomerToDB(this);
            logged = true;
        }
        if (logged == false) {
            return "SignUp.xhtml";
        }
        logIn();
        return "index.xhtml";

    }

    public boolean isLogged() {
        if (logged) {
            return true;
        }
        return false;
    }

    public String isLogged2() {
        if (logged) {
            return "true";
        }
        return "false";
    }

    public String update() {
        if (CheckPassword()) {
            updateMessage = name + ", your account information has been updated";
            LoginBeanDA.updateCustomerToDB(this);
            return "index.xhtml";
        }
        return "Account.xhtml";

    }
}
