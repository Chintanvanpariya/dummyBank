/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

public interface DataStorage {
    
    String createBankAccount(String name, String ssn, double balance);
    String createOnlineAccount(String name, String ssn, String id, String psw);
    String login(String id, String password);
    //ArrayList<Product> getProductAccounts(String ssn);
    ArrayList<BankAccount> getBankAccounts(String ssn);
    ArrayList<String> getBankIDs(String ssn);
    void updateBalance(String accountNumber, double balance, String statement);
    String getSsnByID(String i);
    String updatePassword(String id, String psw);
    //ArrayList<CreditCard> getCreditCardAccounts(String ssn);  
}
