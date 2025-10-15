package co.com.wompi.models;

public class PaymentRequest {
    public int amount_in_cents;
    public String currency;
    public PaymentMethod payment_method;
    public String reference;
    public String customer_email;
    public String acceptance_token;
    public String signature;

    public static class PaymentMethod {
        public String type;
        public int user_type;
        public String user_legal_id;
        public String user_legal_id_type;
        public String financial_institution_code;
        public String payment_description;
    }
}
