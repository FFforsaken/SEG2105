package com.example.java_classes;

public class Cook extends User {

    private boolean available = true; // to be set by temporarily close the user account.
    private boolean indefinitely_Closed = false; // to set a user to indefinitely Close or not.
    private boolean suspend;

    public Cook(String first_name, String last_name, String email_address, String account_password, String address){
        super(first_name, last_name, email_address, account_password, address);
        this.suspend = false;
    }

    public Cook(String email_address, String account_password, String address){
        super(email_address, account_password, address);
    }

    public void setAvailable(boolean true_false){this.available = true_false;}

    public boolean viewAvailable(){return this.available;}

    public void set_indefinitely_Closed(boolean true_false){this.indefinitely_Closed = true_false;}

    public boolean view_indefinitely_Closed(){return this.indefinitely_Closed;}

    public boolean get_suspend(){
        return this.suspend;
    }


    // Short description of themselves
    public String toString(){
        return "Hi, im a cook and my name is " + get_first_name() + get_last_name() + ", i live at " + get_address() +".";
    }
}    
