package co.com.wompi.models;

public class PaymentRequest {
    public int amount_in_cents;
    public String currency;
    public Object payment_method;
    public String reference;
    public String customer_email;
    public String acceptance_token;
    public String signature;

    public static class PaymentMethodPse {
        public String type = "PSE";
        public int user_type;
        public String user_legal_id;
        public String user_legal_id_type;
        public String financial_institution_code;
        public String payment_description;
    }

    public static class PaymentMethodNequi {
        public String type = "NEQUI";
        public String phone_number;
        public String payment_description;
    }
}
