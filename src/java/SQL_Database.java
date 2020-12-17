/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SQL_Database implements DataStorage{
    
    final String DATABASE_URL = 
                "jdbc:mysql://mis-sql.uhcl.edu/drlin?useSSL=false";
    final String db_id = "drlin";
    final String db_psw = "2019drlin";
        
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    
    @Override
    public String createBankAccount(String name, String ssn, double balance)
    {
        try
         {
             //connect tothe databse
             connection = DriverManager.getConnection(DATABASE_URL, 
                     db_id, db_psw);
             //create a statement
             statement = connection.createStatement();
             //create the statement String
             DecimalFormat df = new DecimalFormat("##.00");
             String s = DateAndTime.DateTime()+ ": "
                     + "Account Opened with initial balance $"
                     + df.format(balance) + "\n";
             
             //to get the account number
             resultSet = statement.executeQuery("Select * "
                     + "from nextaccountnumber");
             
             int nextNum = 0;
             String accountNum = "";
             while(resultSet.next())
             {
                 accountNum = "" + resultSet.getInt(1);
                 nextNum = resultSet.getInt(1) + 1;
                 
             }
             
             //rolled back to here if anything wrong
             connection.setAutoCommit(false);
             //update the new nextAccountNum;
             int t = statement.executeUpdate("Update nextAccountNumber set "
                     + "nextID = '" + nextNum + "'");
             //insert a record into bankAccount Table
             int r = statement.executeUpdate("insert into BankAccount values "
                     + "('" + name + "', '" + accountNum + "', '" + ssn + "', '" 
                     + balance + "', '" + s + "')");
             
             connection.commit();
             connection.setAutoCommit(true);
             
              //display msg
            return ("The new bank account is created. "
                    + "The account number is " + accountNum + ".");
             
             
         }
         catch(SQLException e)
         {
             //handle the exceptions
             e.printStackTrace();
             return ("Account creation failed");
              
         }
         finally
         {
             //close the database
             try
             {
                 resultSet.close();
                 statement.close();
                 connection.close();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
    }
    
    @Override
    public String createOnlineAccount(String name, 
            String ssn, String id, String psw)
    {
        
        //Complete the database part below
        final String DATABASE_URL = 
                "jdbc:mysql://mis-sql.uhcl.edu/drlin?useSSL=false";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            
            //connect to the databse
            connection = DriverManager.getConnection(DATABASE_URL, 
                    db_id, db_psw);
            //crate the statement
            statement = connection.createStatement();
            
            //do a query
            resultSet = statement.executeQuery("Select * from onlineAccount "
                    + "where id = '" + id + "' or ssn = '"
                    + ssn + "'");
            
            if(resultSet.next())
            {
                //either the ssn is used or the id is used
                return("Account creation failed");
            }
            else
            {
                //insert a record into onlineAccount
            int r = statement.executeUpdate("insert into onlineAccount values"
                        + "('" + name + "', '" + id + "', '" + ssn + "', '"
                        + psw +"')");
                return ("Account creation successful!");
                 
            }
             
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return("Something wrong during the creation process!");
        }
        finally
        {
             //close the database
             try
             {
                 resultSet.close();
                 statement.close();
                 connection.close();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
        }
        
    }
    
    @Override
    public String login(String id, String password)
    {
        try
        {
            
            //connect to the databse
            connection = DriverManager.getConnection(DATABASE_URL, 
                  db_id, db_psw);
            //create statement
            statement = connection.createStatement();
            //search the accountID in the onlineAccount table
            resultSet = statement.executeQuery("Select * from onlineAccount "
                    + "where id = '" + id + "'");
            
            if(resultSet.next())
            {
                //the id is found, check the password
                if(password.equals(resultSet.getString(4)))
                {
                    //password is good
                    return "welcome";
                    //go to the welcome page 
                }
                else
                {
                    //password is not correct
                    return "loginNotOK";
                     
                }
            }
            else
            {
                return "loginNotOK";
            }
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "internalError";
        }
        finally
        {
            //close the database
            try
            {
                connection.close();
                statement.close();
                resultSet.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
    
    
    @Override 
    public ArrayList<BankAccount> getBankAccounts(String ssn)
    {
        try
        {
            //connect to the databse
            connection = DriverManager.getConnection(DATABASE_URL, 
                  db_id, db_psw);
            //create statement
            statement = connection.createStatement();
            //search the accountID in the onlineAccount table
            resultSet = statement.executeQuery("Select * from bankAccount "
                    + "where ssn = '" + ssn + "'");
            
            ArrayList<BankAccount> aList = new ArrayList<BankAccount>();
            
            while(resultSet.next())
            {
                 BankAccount anAccount = new BankAccount(resultSet.getString(1), 
                 resultSet.getString(2), resultSet.getString(3), 
                 resultSet.getDouble(4), resultSet.getString(5));
                 aList.add(anAccount);
            }
            return aList;
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            //close the database
            try
            {
                connection.close();
                statement.close();
                resultSet.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
    
    @Override 
    public ArrayList<String> getBankIDs(String ssn)
    {
        try
        {
            //connect to the databse
            connection = DriverManager.getConnection(DATABASE_URL, 
                  db_id, db_psw);
            //create statement
            statement = connection.createStatement();
            //search the accountID in the onlineAccount table
            resultSet = statement.executeQuery("Select * from bankAccount "
                    + "where ssn = '" + ssn + "'");
            
            ArrayList<String> aList = new ArrayList<String>();
            
            while(resultSet.next())
            {
                 aList.add(resultSet.getString(2));
            }
            return aList;
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            //close the database
            try
            {
                connection.close();
                statement.close();
                resultSet.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
    
    @Override 
    public void updateBalance(String accountNumber, double balance, 
            String stm)
    {
            
        try
        {
            //connect to the database
            connection = DriverManager.getConnection(DATABASE_URL, 
                    db_id, db_psw);
            connection.setAutoCommit(false);
            //create the statement
            statement = connection.createStatement();
            //update the balance
            int r = statement.executeUpdate("Update bankAccount set "
                    + "balance = '" + balance + "' "
                    + "where accountNumber = '"
                    + accountNumber + "'");
            //update the activity
            r = statement.executeUpdate("Update bankAccount set "
                 + "statement = '" + stm
                    + "' where accountNumber = '"
                    + accountNumber + "'");
            connection.commit();
            connection.setAutoCommit(true);


        }
        catch (SQLException e)
        {
            //handle the exception
            e.printStackTrace();
        }
        finally
        {
            //close the database
            try
            {
                statement.close();
                connection.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    
    @Override
    public String getSsnByID(String i)
    {
        try
        {
            //connect to the database
            connection = DriverManager.getConnection(DATABASE_URL, 
                    db_id, db_psw);
            connection.setAutoCommit(false);
            //create the statement
            statement = connection.createStatement();
            //update the balance
            resultSet = statement.executeQuery("select * from OnlineAccount "
                    + "where id = '" + i + "'");
            
            connection.commit();
            connection.setAutoCommit(true);
            
            if(resultSet.next())
            {
                return resultSet.getString("ssn");
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            //handle the exception
            e.printStackTrace();
            return null;
        }
        finally
        {
            //close the database
            try
            {
                resultSet.close();
                statement.close();
                connection.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String updatePassword(String id, String psw)
    {
        try 
        {
            //connect to the database
            connection = DriverManager.getConnection(DATABASE_URL, 
                    db_id, db_psw);

            //create a statement
            statement = connection.createStatement();

            int r = statement.executeUpdate("Update onlineaccount set "
                    + "psw = '" +  psw + "' where id= '" + id + "'");
            return("confirmationResetOK");

        }
        catch (SQLException e)
        {

            e.printStackTrace();
            return("internalError");
         }
         finally
         {
            try
            {
                statement.close();
                connection.close();

            }
            catch (Exception e)
            {                 
                e.printStackTrace();
            }
         }
             
        
    }

}
