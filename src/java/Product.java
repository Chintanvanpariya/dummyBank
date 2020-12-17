

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public abstract class Product {
    
    private String name;
    private String ssn;
    private String type;
    
    public Product(String n, String s, String t)
    {
        name = n;
        ssn = s;
        type = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getType() {
        return type;
    }
    
    public abstract void showStatement();
    
}
