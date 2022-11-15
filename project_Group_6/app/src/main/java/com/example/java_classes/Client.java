package com.example.java_classes;

public class Client extends User{


    private String credit_card_number;
    private String credit_card_expiration_date_month_year;
    private String CVV;




    public Client(String first_name, String last_name, String email_address, String account_password, String address, String credit_card_number, String credit_card_expiration_date_month_year, String CVV){
        super(first_name, last_name, email_address, account_password, address);

        this.credit_card_number = credit_card_number;
        this.credit_card_expiration_date_month_year = credit_card_expiration_date_month_year;
        this.CVV = CVV;
    }
    public Client(String email_address, String account_password, String address){
        super(email_address, account_password, address);
    }

    public String get_credit_card_number(){
        return this.credit_card_number;
    }



    public String get_credit_card_expiration_date(){
        return this.credit_card_expiration_date_month_year;
    }

    public String get_CVV(){
        return this.CVV;
    }


}