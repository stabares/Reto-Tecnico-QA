package co.com.wompi.tasks;

import co.com.wompi.models.PaymentRequest;
import co.com.wompi.utils.Constants;
import co.com.wompi.utils.SignatureUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import static co.com.wompi.utils.Constants.PRIVATE_KEY;

public class CreateSuccessfulPayment implements Task {
    @Override
    @Step("Crea una transaccion exitosa")
    public <T extends Actor> void performAs(T actor) {
        String token = SignatureUtils.getAcceptanceToken();

        PaymentRequest payment = new PaymentRequest();
        payment.amount_in_cents = 2500000;
        payment.currency = "COP";
        payment.customer_email = "prueba.testing@yopmail.com";
        payment.reference = "REF-WOMPI-" + System.currentTimeMillis();
        payment.acceptance_token = token;
        payment.signature = SignatureUtils.generateSignature(payment.reference, payment.amount_in_cents, payment.currency);

        PaymentRequest.PaymentMethodPse pse = new PaymentRequest.PaymentMethodPse();
        pse.user_type = 0;
        pse.user_legal_id = "123456789";
        pse.user_legal_id_type = "CC";
        pse.financial_institution_code = "1";
        pse.payment_description = "Probando pago a Tienda Wompi";

        payment.payment_method = pse;

        SerenityRest
                .given()
                .baseUri(Constants.BASE_URL)
                .contentType("application/json")
                .header("Authorization", "Bearer "+ PRIVATE_KEY)
                .body(payment)
                .post("/transactions")
                .then()
                .log().all();
    }

    public static CreateSuccessfulPayment transaction() {
        return Tasks.instrumented(CreateSuccessfulPayment.class);
    }
}
