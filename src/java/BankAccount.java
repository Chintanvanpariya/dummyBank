/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BankAccount extends Product{
    
     
    private double balance;
    private String accountNumber;
    private String stm;
    private DataStorage data = new SQL_Database();
    
    public BankAccount(String n, String a, String s, double b, String st)
    {
        super(n, s, "Bank Account");
        balance = b;
        accountNumber = a;
        stm = st;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }  
    
    public void deposit(double da)
    {
        if(da > 0.0)
        {
            balance = balance + da;
            DecimalFormat df = new DecimalFormat("##.00");
            stm = stm + DateAndTime.DateTime() + ": Deposit " 
                 + df.format(da) 
                    + ". Balance: $" + df.format(balance) + "\n";
            
            data.updateBalance(accountNumber, balance, stm);
             
        }
    }
    
    public void withdraw(double wa)
    {
        if((balance - wa) >= 0.0)
        {
            balance = balance - wa;
            DecimalFormat df = new DecimalFormat("##.00");
            stm = stm + DateAndTime.DateTime() + ": Withdraw " 
                  + df.format(wa) + ". Balance: $" 
                    + df.format(balance) + "\n";
            data.updateBalance(accountNumber, balance, stm);
        }
    }

    @Override
    public void showStatement()
    {
        System.out.println(stm);
    }
    
    public List<String> displayStatement()
    {
        List statements = new ArrayList<String> ();
        String[] statement =  stm.split("\n"); 
        
        for(int i=0; i<statement.length; i++)
        {
            statements.add(statement[i]);
        }
        
        return statements;
    }
}
