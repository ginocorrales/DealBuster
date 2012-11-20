/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my_beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;
import java.util.*;
import java.util.Calendar;


/**
 *
 * @author david
 */
public class DealBeanDA {


    public static String getDeal(DealBean deal){
        String dbPic = null;
        String dbTitle = null;
        String dbPrice = null;
        String dbQty = null;

        //DEALNUMBER
        
       Calendar now =  Calendar.getInstance();
       int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);


        //GET INTERESTS
        String temp = null;
       // String[] interests = deal.getInterests();
//        for(int i = 0 ; i < interests.length;i++)
//        {
//            temp += interests[i];
//        }

        Connection con;
        Statement ps;
        ResultSet rs;
        String SQL_Str;
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                String myDB = "jdbc:derby://localhost:1527/deals";
                con = DriverManager.getConnection(myDB, "itkstu", "student");
                ps = con.createStatement();
                SQL_Str = "select * from DEALS.DEALOFDAY WHERE DEALNUM = "+dayOfMonth+" AND INTERESTGROUP = 'tech'";

                //get interests from DB INTERESTGROUP = 'tech' AND
//                for(int j = 0; j<interests.length; j++)
//                {
//                SQL_Str +=" AND WHERE INTERESTGROUP = '"+ interests[j] +"' ";
//                }
//
                rs = ps.executeQuery(SQL_Str);
                rs.next();
                dbPic =  rs.getString(1).toString();
                
                dbTitle = rs.getString(2).toString();
                dbPrice = rs.getString(3).toString();
                dbQty = rs.getString(4).toString();

                deal.setTitle(dbTitle);
                deal.setAvailQty(dbQty);
                deal.setPrice(dbPrice);
                deal.setPic(dbPic);
                

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Exception Occur :" + ex);
            }
            return dbPic + ", " + dbTitle + "<br/>Price: $" + dbPrice + "<br/>Quantity: " + dbQty;
        }

}
