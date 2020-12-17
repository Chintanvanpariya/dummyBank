/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Login implements Serializable{

    private String id;
    private String password;
    private OnlineAccount theLoginAccount;
    
    //get methods and set methods
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public OnlineAccount getTheLoginAccount() {
        return theLoginAccount;
    }
    

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    public String login()
    {
        //load the Driver
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }
        
        DataStorage data = new SQL_Database();
        
        String fileName = data.login(id, password);
        
        if(fileName.equals("welcome"))
        {
            theLoginAccount = new OnlineAccount(id, password);
            theLoginAccount.setData(data);
            String userSsn = data.getSsnByID(id);
            theLoginAccount.setSsn(userSsn);
            theLoginAccount.updateBankAccounts();
            theLoginAccount.updateBankIDs();
            return "welcome";
        }
        else
        {
            return fileName;
        }
          
         
    }
    
}
