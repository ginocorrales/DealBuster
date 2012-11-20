/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_beans;

/**
 *
 * @author BillyLim
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the data access counterpart for ProfileBean. It uses the JDBC protocol
 * to connect to and store a given profile in a Derby DB.
 *
 * @author bllim
 */
public class LoginBeanDA {

    public static int storeCustomerToDB(LoginBean log) {

        Connection con;
        Statement ps;
        ResultSet rs;
        String SQL_Str;
        String[] interests;
        String temp = "";
        int rowCount = 0;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginBeanDA.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/deals";
            con = DriverManager.getConnection(myDB, "itkstu", "student");
            ps = con.createStatement();
            interests = log.getInterests();

            for (int i = 0; i < interests.length; i++) {
                temp += interests[i] + "; ";
            }
            SQL_Str = "INSERT INTO deals.Users VALUES ('" + log.getName() + "', '" + log.getEmail() + "', '" + log.getPassword() + "', '" + temp + "')";
            rowCount = ps.executeUpdate(SQL_Str);
            con.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }

    public static int updateCustomerToDB(LoginBean log) {
        Connection con;
        Statement ps;
        ResultSet rs;
        String SQL_Str;
        String[] interests;
        String temp = "";
        int rowCount = 0;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginBeanDA.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String myDB = "jdbc:derby://localhost:1527/deals";
            con = DriverManager.getConnection(myDB, "itkstu", "student");
            ps = con.createStatement();
            interests = log.getInterests();
            for (int i = 0; i < interests.length; i++) {
                temp += interests[i] + "; ";
            }
            SQL_Str = "UPDATE deals.Users SET PASSWORD=('" + log.getPassword() + "'), INTERESTS=('" + temp + "')"
                    + "WHERE email=('" + log.getEmail() + "')";
            rowCount = ps.executeUpdate(SQL_Str);
            con.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // if insert is successful, rowCount will be set to 1 (1 row inserted successfully). Else, insert failed.
        return rowCount;

    }

    public static boolean getProfileFromDB(LoginBean log) {
        String dbName = null;
        String dbEmail = null;
        String dbPassword = null;
        String dbInterests = null;
        String tempInterests = null;
        String[] interestsDA = new String[3];
        char compare = ';';
        int tempInt = 0;
        int arrayCount = 0;
        Connection con;
        Statement ps;
        ResultSet rs;
        String SQL_Str;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String myDB = "jdbc:derby://localhost:1527/deals";
            con = DriverManager.getConnection(myDB, "itkstu", "student");
            ps = con.createStatement();
            SQL_Str = "Select fullname, email, password, interests from deals.Users where email like ('" + log.getEmail() + "')";
            rs = ps.executeQuery(SQL_Str);
            rs.next();
            dbName = rs.getString(1).toString();
            dbEmail = rs.getString(2).toString();
            dbPassword = rs.getString(3).toString();
            dbInterests = rs.getString(4).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception Occur :" + ex);
        }
        if (log.getEmail().equals(dbEmail)) {



            if (log.getPassword().equals(dbPassword)) {
                log.setName(dbName);
                log.setEmail(dbEmail);
                log.setPassword(dbPassword);
                for (int i = 0; i < dbInterests.length(); i++) {
                    if (dbInterests.charAt(i) == compare) {
                        tempInterests = dbInterests.substring(tempInt, i);
                        interestsDA[arrayCount] = tempInterests;
                        arrayCount++;
                        tempInt = i + 2;
                    }
                }
                log.setInterests(interestsDA);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean getEmailAvailableFromDB(LoginBean log) {

        String dbEmail = null;
        Connection con;
        Statement ps;
        ResultSet rs;
        String SQL_Str;
        try {

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String myDB = "jdbc:derby://localhost:1527/deals";
            con = DriverManager.getConnection(myDB, "itkstu", "student");
            ps = con.createStatement();
            SQL_Str = "Select email from deals.Users where email like ('" + log.getEmail() + "')";
            rs = ps.executeQuery(SQL_Str);
            rs.next();
            dbEmail = rs.getString(1).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception Occur :" + ex);
        }
        if (log.getEmail().equals(dbEmail)) {

            return false;
        } else {
            return true;
        }
    }
}