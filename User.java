

public abstract class User{
    
    private String first_name;
    private String last_name; 
    private String email_address;
    private String user_name = email_address;
    private String account_password;
    private String address;

    public User(String first_name, String last_name, String email_address, String account_password, String address){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_address = email_address;
        this.account_password = account_password;
        this.address = address;
    }



    public String get_first_name(){
        return this.first_name;
    }

    public String get_last_name(){
        return this.last_name;
    }

    public String get_email_address(){
        return this.email_address;
    }

    public String get_user_name(){
        return this.user_name;
    }

    public String get_account_password(){
        return this.account_password;
    }
    public String get_address(){
        return this.address;
    }

    public void set_first_name(String firstname){
        this.first_name = firstname;
    }

    public void set_last_name(String lastname){
        this.last_name = lastname;
    }
    public void set_email_address(String email_address){
        this.email_address = email_address;
    }

    public void set_account_password(String account_password){
        this.account_password = account_password;
    }

    public void set_address(String address){
        this.address = address;
    }

}