/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import javax.faces.context.FacesContext;

public class OnlineAccount {
    
    //attributes
    private String ssn;
    private String id;
    private String psw;
    private DataStorage data;
    private String selectedAccountToView;  
    private String fromAccount;
    private String toAccount;
    private double transferAmount;
    private String oldPsw;
    private String newPsw1;
    private String newPsw2;
    
    //we are coming back
    //a set of bank accounts
    private ArrayList<BankAccount> bankAccounts
            = new ArrayList<BankAccount>();
    private ArrayList<String> bankIDs = new ArrayList<String>();
    
    //constructor
    public OnlineAccount(String i, String p)
    {
        id = i;
        psw = p;
    }
    
    public void updateBankAccounts()
    {
        bankAccounts = data.getBankAccounts(ssn);
    }
    
    public void updateBankIDs()
    {
        bankIDs = data.getBankIDs(ssn);
    }
    
    
    public String accountTransfer()
    {
         
        int toIndex = 0; 
        int fromIndex = 0;
        for(int i=0; i<bankAccounts.size(); i++)
        {
            if(bankAccounts.get(i).getAccountNumber().equals(toAccount))
            {
                toIndex = i;
            }
            
            if(bankAccounts.get(i).getAccountNumber().equals(fromAccount))
            {
                fromIndex = i;
            }
            
        }
            
            
            
        if(bankAccounts.size() < 2)
        {
            fromAccount = bankAccounts.get(0).getAccountNumber();
            toAccount = bankAccounts.get(0).getAccountNumber();
            transferAmount = 0.0;
             return("You must have at least two different online accounts to do account transfer");
        }
        else
        { 
             if(!fromAccount.equals(toAccount))
             {
                 if(bankAccounts.get(fromIndex).getBalance() < transferAmount)
                 {
                     fromAccount = bankAccounts.get(0).getAccountNumber();
                     toAccount = bankAccounts.get(0).getAccountNumber();
                     transferAmount = 0.0;
                     
                     return("The account used to transfer the money from has no enough fund!");
                 }
                 else
                 {
                     bankAccounts.get(toIndex).deposit(transferAmount);
                     bankAccounts.get(fromIndex).withdraw(transferAmount);
                          
                     fromAccount = bankAccounts.get(0).getAccountNumber();
                     toAccount = bankAccounts.get(0).getAccountNumber();
                     transferAmount = 0.0;
                     return("The transfer of money was successful!");
                 }
             }
             else
             {
                 fromAccount = bankAccounts.get(0).getAccountNumber();
                 toAccount = bankAccounts.get(0).getAccountNumber();
                 transferAmount = 0.0;
                 return ("You must select two different accounts");
                             
             }
        }
 
    }
   
   
    public String resetPassword()
    {
        boolean newPswOK = false; 
        boolean matchOldPsw = false;
        
        
        
        if(newPsw1.equals(newPsw2))
        {
            newPswOK = true;
        }
        
        if(oldPsw.equals(psw))
        {
            matchOldPsw = true;
        }
        
        if(!newPswOK)
        {
            return ("confirmationResetWrong.xhtml");
        }
        else if(!matchOldPsw)
        {   
            return ("confirmationResetWrong.xhtml");   
        }
        else
        {
            DataStorage data = new SQL_Database();
            return data.updatePassword(id, newPsw1);
        }
        
    }
    
    //get methods and set methods
    public String getSsn() {
        return ssn;
    }

     

    public String getId() {
        return id;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

     
    public DataStorage getData() {
        return data;
    }

    public void setData(DataStorage data) {
        this.data = data;
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(ArrayList<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

     

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getBankIDs() {
        return bankIDs;
    }

    public void setBankIDs(ArrayList<String> bankIDs) {
        this.bankIDs = bankIDs;
    }

    public String getSelectedAccountToView() {
        return selectedAccountToView;
    }

    public void setSelectedAccountToView(String selectedAccountToView) {
        this.selectedAccountToView = selectedAccountToView;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getOldPsw() {
        return oldPsw;
    }

    public void setOldPsw(String oldPsw) {
        this.oldPsw = oldPsw;
    }

    public String getNewPsw1() {
        return newPsw1;
    }

    public void setNewPsw1(String newPsw1) {
        this.newPsw1 = newPsw1;
    }

    public String getNewPsw2() {
        return newPsw2;
    }

    public void setNewPsw2(String newPsw2) {
        this.newPsw2 = newPsw2;
    }
    
    
    
    
    public List<String> showSelectedStatement()
    {
        int index = -1;
        for(int i=0; i<bankAccounts.size(); i++)
        {
      if(bankAccounts.get(i).getAccountNumber().equals(selectedAccountToView))
            {
                index = i;
                break;
            }
        }
        
        if(index < 0)
        {
            return null;
        }
        else
        {
            return bankAccounts.get(index).displayStatement();
        }
        
    }
    
    
    
     
    
    public String signOut()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";

        
    }
}


 