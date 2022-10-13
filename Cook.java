
public class Cook extends User{

    private String Picture_of_a_void_cheque;  ///???? question to ask the TA
    
    public Cook(String first_name, String last_name, String email_address, String account_password, String address){
        super(first_name, last_name, email_address, account_password, address);
    }

    
    // Short description of themselves
    public String toString(){
        return "Hi, im a cook and my name is " + get_first_name() + get_last_name() + ", i live at " + get_address() +".";
    }
}    
