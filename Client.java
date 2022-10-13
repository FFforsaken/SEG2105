
public class Client extends User{


    private long credit_card_number;
    private int credit_card_expiration_date_month;
    private int credit_card_expiration_date_year;
    private int CVV;




    public Client(String first_name, String last_name, String email_address, String account_password, String address, long credit_card_number, int credit_card_expiration_date_month, int credit_card_expiration_date_year, int CVV){
        super(first_name, last_name, email_address, account_password, address);

        this.credit_card_number = credit_card_number;
        this.credit_card_expiration_date_month = credit_card_expiration_date_month;
        this.credit_card_expiration_date_year = credit_card_expiration_date_year;
        this.CVV = CVV;
    }


    public get_credit_card_number(){
        return this.credit_card_number;
    }   
    
    public get_credit_card_expiration_date_month(){
        return this.credit_card_expiration_date_month;
    }
    
    public get_credit_card_expiration_date_year(){
        return this.credit_card_expiration_date_year;
    }

    public get_CVV(){
        return this.CVV;
    }
    

}